package dsx.bcv.services;

import com.google.gson.Gson;
import dsx.bcv.data.models.Bar;
import dsx.bcv.data.models.CurrencyPair;
import javafx.util.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class CurrencyPairService {
    private ArrayList<Pair<String,ArrayList<Bar>>>  getHistoricalData(int numberOfBars) throws IOException, ParseException {
        var result =  getHistoricalData(numberOfBars, "btgeur-btcusdt-btcrub-eosbtc-bcceurs-eurtry-btctry-btcgbp-" +
                "eurseur-ltcbtc-bccusdt-btggbp-eursusd-ethgbp-btgbtc-usdrub-bccbtc-ltcusd-bccusd-btcusd-ltceur-eoseth-bsveur");
        result.addAll(getHistoricalData(numberOfBars,"bcceur-ltctry-btceur-btgusd-usdtry-ethusd-etheur-ltcgbp-bsvusd-" +
                "bsvbtc-gbpusd-bccgbp-ethtry-eoseur-bsveth-ethbtc-ltcusdt-btceurs-usdtusd-eosusd-ltceurs-usdteur-etheurs-eurusd-ethusdt"));
    return result;
    }

    private ArrayList<Pair<String, ArrayList<Bar>>> getHistoricalData(int numberOfBars, String currencyPairs) throws IOException, ParseException {
        String response = getResponse("https://dsx.uk/mapi/lastBars/" + currencyPairs +"/h/" + numberOfBars + "?mode=LIVE").toString();
        JSONParser parser = new JSONParser();
        JSONObject parseResult = (JSONObject) parser.parse(response);
        var keySet = parseResult.keySet();
        ArrayList<Pair<String, ArrayList<Bar>>> results = new ArrayList<Pair<String, ArrayList<Bar>>>();
        Gson g = new Gson();
        for (var key: keySet) {
            ArrayList<Bar> bars = new ArrayList<Bar>();
            for (var json: (JSONArray) parseResult.get(key)) {
                bars.add(g.fromJson(json.toString(),Bar.class));
            }
            results.add(new Pair<>(key.toString(), bars));
        }
        return results;
    }

    public void printHistoricalData(int numberOfBars, String outputPath) throws IOException, ParseException {
        printHistoricalData(numberOfBars,outputPath,',');
    }

    private void printHistoricalData(int numberOfBars, String outputPath,char separator) throws IOException, ParseException {
        ArrayList<Pair<String, ArrayList<Bar>>> historicalData =  getHistoricalData(numberOfBars);
        FileOutputStream out = new FileOutputStream(outputPath);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.newFormat(separator));
        for (var pair: historicalData){
            for (var bar: pair.getValue())
            {
                printer.printRecord(pair.getKey() + ',' + bar.toString() + '\n');
            }
        }
        printer.close();
        writer.close();
        out.close();
    }

    public ArrayList<Pair<String, CurrencyPair>> getActualData() throws IOException, ParseException {
        String response = getResponse("https://dsx.uk/mapi/info?mode=LIVE").toString();
        JSONParser parser = new JSONParser();
        JSONObject parseResult = (JSONObject) parser.parse(response);
        long serverTime = (long)parseResult.get("server_time");
        parseResult = (JSONObject)parseResult.get("pairs");
        var keySet = parseResult.keySet();
        ArrayList<Pair<String, CurrencyPair>> results = new ArrayList<Pair<String, CurrencyPair>>();
        Gson g = new Gson();
        for (var key: keySet) {
            JSONObject json = (JSONObject)parseResult.get(key);
            results.add(new Pair<>(key.toString(),g.fromJson(json.toString(),CurrencyPair.class)));
        }
        return results;
    }

    private StringBuffer getResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }
}
