package dsx.bcv.marketdata_provider.controllers;

import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.models.Instrument;
import dsx.bcv.marketdata_provider.data.models.Tiker;
import dsx.bcv.marketdata_provider.services.QuoteProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("quotes")
public class QuoteProviderController {

    private final QuoteProvider provider;

    public QuoteProviderController() throws IOException {
        this.provider = new QuoteProvider();
    }

    public QuoteProviderController(List<String> idsList) throws IOException {
        this.provider = new QuoteProvider(idsList);
    }

    @GetMapping("tikers")
    public List<Tiker> getActualData(){return provider.getActualData(); }

    @GetMapping("bars/{idsList}/{period}/{amount}")
    public List<Bar> getHistoricalData(@PathVariable String idsList, @PathVariable String period, @PathVariable int amount) throws IOException {
        return provider.getHistoricalData(Arrays.asList(idsList.split("-")),period,amount);
    }

    @GetMapping("bars/{period}/{amount}")
    public List<Bar> getHistoricalData(@PathVariable String period, @PathVariable int amount) throws IOException {
        return provider.getHistoricalData(period,amount);
    }

    @GetMapping("bars/{amount}")
    public List<Bar> getHistoricalData(@PathVariable int amount) throws IOException {
        return provider.getHistoricalData(amount);
    }

    @GetMapping("bars/printData/{idsList}/{period}/{amount}")
    public void printHistoricalData(@PathVariable String idsList, @PathVariable String period, @PathVariable int amount) throws IOException {
        String path = System.getProperty("user.dir") + "\\historicalData.csv";
        provider.printHistoricalData(Arrays.asList(idsList.split("-")),period,amount,path);
    }

    @GetMapping("bars/printData/{period}/{amount}")
    public void printHistoricalData(@PathVariable String period, @PathVariable int amount) throws IOException {
        String path = System.getProperty("user.dir") + "\\historicalData.csv";
        provider.printHistoricalData(period,amount,path);
    }

    @GetMapping("bars/printData/{amount}")
    public void printHistoricalData(@PathVariable int amount) throws IOException {
        String path = System.getProperty("user.dir") + "\\historicalData.csv";
        provider.printHistoricalData(amount,path);
    }

    @GetMapping("info")
    public List<Instrument> getInfo() throws IOException { return provider.receiveInfo(); }
}
