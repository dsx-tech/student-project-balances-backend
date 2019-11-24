package dsx.bcv.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dsx.bcv.data.models.Bar;
import dsx.bcv.data.models.Instrument;
import dsx.bcv.data.models.Tiker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QuoteProvider {
    private final URLFetcher fetcher;
    private volatile List<Tiker> actualData;
    private final ObjectMapper mapper;
    private final Formatter formatter;

    QuoteProvider() throws IOException {
        this.mapper = new ObjectMapper();
        this.fetcher = new URLFetcher();
        this.formatter = new Formatter();
        this.actualData = receiveActualData();
    }

    public List<Tiker> getActualData() {
        return actualData;
    }

    private List<Tiker> receiveActualData(List<String> idsList) throws IOException {
        String response = fetcher.getDsxTikers(idsList);
        return parseActualData(response);
    }

    private List<Tiker> receiveActualData() throws IOException {
        String response = fetcher.getDsxTikers();
        return parseActualData(response);
    }

    private List<Tiker> parseActualData(String response) throws IOException {
        JsonNode rootNode = mapper.readValue(response, JsonNode.class);
        List<Tiker> result = new ArrayList<>();
        var iterator = rootNode.fieldNames();
        while (iterator.hasNext()) {
            String id = iterator.next();
            result.add(mapper.readerFor(Tiker.class).readValue(rootNode.get(id)));
        }
        return result;
    }

    public void startReceivingActualData(List<String> idsList) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            try {
                this.actualData = receiveActualData(idsList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    public void startReceivingActualData() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            try {
                this.actualData = receiveActualData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 60, TimeUnit.SECONDS);
    }

    private List<Bar> getHistoricalData(int amount) throws IOException {
        String response = fetcher.getDsxLastBars(amount);
        return parseHistoricalData(response);
    }

    private List<Bar> getHistoricalData(String period, int amount) throws IOException {
        String response = fetcher.getDsxLastBars(period, amount);
        return parseHistoricalData(response);
    }

    private List<Bar> getHistoricalData(List<String> idsList, String period, int amount) throws IOException {
        String response = fetcher.getDsxLastBars(idsList, period, amount);
        return parseHistoricalData(response);
    }

    private List<Bar> parseHistoricalData(String response) throws IOException {
        JsonNode rootNode = mapper.readValue(response, JsonNode.class);
        ArrayList<Bar> result =  new ArrayList<Bar>();
        var iterator = rootNode.fieldNames();
        while (iterator.hasNext()){
            String id = iterator.next();
            InjectableValues inject = new InjectableValues.Std().addValue(String.class, id);
            result.addAll(Arrays.asList(mapper.reader(inject).forType(Bar[].class).readValue(rootNode.get(id))));
        }
        result.sort(Comparator.comparingLong(bar -> ((Bar)bar).getTimestamp()).thenComparing(bar -> ((Bar)bar).getId()));
        return result;
    }

    void printHistoricalData(int amount, String outputPath) throws IOException {
        List<Bar> bars = getHistoricalData(amount);
        printBarsToFile(bars,outputPath);
    }

    void printHistoricalData(String period, int amount, String outputPath) throws IOException {
        List<Bar> bars = getHistoricalData(period, amount);
        printBarsToFile(bars,outputPath);
    }

    void printHistoricalData(List<String> idsList,String period, int amount, String outputPath) throws IOException {
        List<Bar> bars = getHistoricalData(idsList, period, amount);
        printBarsToFile(bars,outputPath);
    }

    private void printBarsToFile(List<Bar> bars, String outputPath) throws IOException {
        FileOutputStream out = new FileOutputStream(outputPath);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.newFormat(','));
        for (var bar : bars) {
            printer.printRecord( bar.toCsvRecord(this.formatter));

        }
        printer.close();
        writer.close();
        out.close();
    }

    public List<Instrument> receiveInfo() throws IOException {
        String response = fetcher.getDsxInfo();
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
}
