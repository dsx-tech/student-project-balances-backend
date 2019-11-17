package dsx.bcv.services;


import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class URLFetcher {
    URLFetcher() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config.properties"));
    }

    List<String> getSupportedPairs() {
        String strResult = properties.getProperty("FIRST_PART_OF_SUPPORTED_PAIRS_IDS") + '-'
                + properties.getProperty("SECOND_PART_OF_SUPPORTED_PAIRS_IDS");
        return Arrays.asList(strResult.split("-"));
    }

    String getDsxInfo() throws IOException {
        return getResponse(properties.getProperty("DSX_MAPI_INFO_URL"));
    }

    String getDsxLastBars() throws IOException {
        return getDsxLastBars(Integer.parseInt(properties.getProperty("DEFAULT_AMOUNT_OF_BARS")));
    }

    String getDsxLastBars(int amount) throws IOException {
        return getDsxLastBars(properties.getProperty("DEFAULT_PERIOD"), amount);
    }

    String getDsxLastBars(String period, int amount) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append(getDsxLastBars(Arrays.asList(properties.getProperty("FIRST_PART_OF_SUPPORTED_PAIRS_IDS").split("-")), period, amount));
        result.deleteCharAt(result.length() - 1);
        result.append(',');
        String secondBarsResponse = getDsxLastBars(Arrays.asList(properties.getProperty("SECOND_PART_OF_SUPPORTED_PAIRS_IDS").split("-")), period, amount);
        result.append(secondBarsResponse, 1, secondBarsResponse.length());
        return result.toString();
    }

    String getDsxLastBars(List<String> idsList, String period, int amount) throws IOException {
        return getResponse(properties.getProperty("DSX_MAPI_LAST_BARS_URL")
                .replace("{period}", period)
                .replace("{amount}", String.valueOf(amount))
                .replace("{pair}", generatePairsStrFromList(idsList)));
    }

    String getDsxTikers() throws IOException {
        StringBuilder result = new StringBuilder();
        String urlTikerPattern = properties.getProperty("DSX_MAPI_TIKERS_URL");
        result.append(getResponse(urlTikerPattern.replace("{pair}", properties.getProperty("FIRST_PART_OF_SUPPORTED_PAIRS_IDS"))));
        result.deleteCharAt(result.length() - 1);
        result.append(',');
        String secondTikersResponse = getResponse(urlTikerPattern.replace("{pair}", properties.getProperty("SECOND_PART_OF_SUPPORTED_PAIRS_IDS")));
        result.append(secondTikersResponse, 1, secondTikersResponse.length());
        return result.toString();
    }

    String getDsxTikers(List<String> idsList) throws IOException {
        return getResponse(properties.getProperty("DSX_MAPI_TIKERS_URL")
                .replace("{pair}", generatePairsStrFromList(idsList)));
    }

    private String getResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private String generatePairsStrFromList(List<String> idsList){
        String result = idsList.toString().substring(1, idsList.toString().length() - 1) ;
        return result.replaceAll(", ","-");
    }

    private final Properties properties;
}
