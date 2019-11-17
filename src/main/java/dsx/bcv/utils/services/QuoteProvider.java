package dsx.bcv.utils.services;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.utils.data.models.Bar;
import dsx.bcv.utils.data.models.Instrument;
import dsx.bcv.utils.data.models.Tiker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QuoteProvider {
    public QuoteProvider() throws IOException {
        this.fetcher = new URLFetcher();
        this.actualData = receiveActualData();
    }

    public List<Tiker> getActualData(){
        synchronized(actualData){
            return actualData;
        }
    }

    private List<Tiker> receiveActualData() throws IOException {
        String response = fetcher.getDsxTikers();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(response, JsonNode.class);
        List<Tiker> result = new ArrayList<>();
        var iterator = rootNode.fieldNames();
        while (iterator.hasNext()) {
            String id = iterator.next();
            result.add(mapper.readerFor(Tiker.class).readValue(rootNode.get(id)));
        }
        return result;
    }

    public void startReceivingActualData() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            synchronized(actualData){
                try {
                    actualData.clear();
                    actualData.addAll(receiveActualData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    private List<Bar> getHistoricalData(int amount) throws IOException {
        String response = fetcher.getDsxLastBars(amount);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(response, JsonNode.class);
        ArrayList<Bar> results =  new ArrayList<>();
        var iterator = rootNode.fieldNames();
        while (iterator.hasNext()){
            String id = iterator.next();
            InjectableValues inject = new InjectableValues.Std().addValue(String.class, id);
            results.addAll(Arrays.asList(mapper.reader(inject).forType(Bar[].class).readValue(rootNode.get(id))));
        }
        results.sort((fisrtBar, secondBar) -> {
            long fBarTime = fisrtBar.getTimestamp();
            long sBarTime = secondBar.getTimestamp();
            if (fBarTime < sBarTime){
                return -1;
            }
            if (fBarTime > sBarTime){
                return 1;
            }
            if (fBarTime == sBarTime){
                return fisrtBar.getId().compareTo(secondBar.getId());
            }
            return 0;
        });
        return results;
    }

    public void printHistoricalData(int amount, String outputPath) throws IOException {
        printHistoricalData(amount, outputPath, ',');
    }

    private void printHistoricalData(int amount, String outputPath, char separator) throws IOException {
        List<Bar> bars = getHistoricalData(amount);
        FileOutputStream out = new FileOutputStream(outputPath);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.newFormat(separator));
        for (var bar : bars) {
                printer.printRecord( bar.toCsvRecord());

        }
        printer.close();
        writer.close();
        out.close();
    }

    public List<Instrument> receiveInfo() throws IOException {
        String response = fetcher.getDsxInfo();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(response, JsonNode.class);
        JsonNode pairsNode = rootNode.get("pairs");
        ArrayList<Instrument> results = new ArrayList<>();
        var iterator = pairsNode.fieldNames();
        while (iterator.hasNext()) {
            String id = iterator.next();
            InjectableValues inject = new InjectableValues.Std().addValue(String.class, id);
            results.add(mapper.reader(inject).forType(Instrument.class).readValue(pairsNode.get(id)));
        }
        return results;
    }

    private final URLFetcher fetcher;
    private final List<Tiker> actualData;
}
