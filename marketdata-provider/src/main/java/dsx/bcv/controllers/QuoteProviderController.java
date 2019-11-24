package dsx.bcv.controllers;

import dsx.bcv.data.models.Bar;
import dsx.bcv.data.models.Instrument;
import dsx.bcv.data.models.Tiker;
import dsx.bcv.services.QuoteProvider;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("tikers")
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

    @RequestMapping("bars/printData/{idsList}/{period}/{amount}")
    public void printHistoricalData(@PathVariable String idsList, @PathVariable String period, @PathVariable int amount) throws IOException {
        String path = System.getProperty("user.dir") + "\\historicalData.csv";
        provider.printHistoricalData(Arrays.asList(idsList.split("-")),period,amount,path);
    }

    @RequestMapping("bars/printData/{period}/{amount}")
    public void printHistoricalData(@PathVariable String period, @PathVariable int amount) throws IOException {
        String path = System.getProperty("user.dir") + "\\historicalData.csv";
        provider.printHistoricalData(period,amount,path);
    }

    @RequestMapping("bars/printData/{amount}")
    public void printHistoricalData(@PathVariable int amount) throws IOException {
        String path = System.getProperty("user.dir") + "\\historicalData.csv";
        provider.printHistoricalData(amount,path);
    }

    @RequestMapping("info")
    public List<Instrument> getInfo() throws IOException { return provider.receiveInfo(); }
}
