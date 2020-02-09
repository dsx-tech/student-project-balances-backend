package dsx.bcv.marketdata_provider.controllers;

import dsx.bcv.marketdata_provider.data.dto.BarDTO;
import dsx.bcv.marketdata_provider.data.dto.TickerDTO;
import dsx.bcv.marketdata_provider.services.QuotesProviderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("quotes")
public class QuotesProviderController {

    private final QuotesProviderService quotesProviderService;

    public QuotesProviderController(QuotesProviderService quotesProviderService) {
        this.quotesProviderService = quotesProviderService;
    }

    @ApiOperation("Get bars for every day from startTime to endTime in unix format (https://www.epochconverter.com).\n" +
            "Supported instruments:\n" +
            "btgeur\n" +
            "eosbtc\n" +
            "btctry\n" +
            "bcceurs\n" +
            "xrpeur\n" +
            "eursusd\n" +
            "btgbtc\n" +
            "usdrub\n" +
            "bccbtc\n" +
            "ltcusd\n" +
            "bsveur\n" +
            "xrpbtc\n" +
            "bcceur\n" +
            "btceur\n" +
            "ethusd\n" +
            "bsvbtc\n" +
            "gbpusd\n" +
            "bccgbp\n" +
            "eoseur\n" +
            "ltcusdt\n" +
            "usdtusd\n" +
            "etheurs\n" +
            "btcusdt\n" +
            "btcrub\n" +
            "eurtry\n" +
            "btcgbp\n" +
            "eurseur\n" +
            "ltcbtc\n" +
            "bccusdt\n" +
            "btggbp\n" +
            "ethgbp\n" +
            "xrpusd\n" +
            "bccusd\n" +
            "btcusd\n" +
            "ltceur\n" +
            "eoseth\n" +
            "ltctry\n" +
            "btgusd\n" +
            "usdtry\n" +
            "etheur\n" +
            "ltcgbp\n" +
            "bsvusd\n" +
            "ethtry\n" +
            "bsveth\n" +
            "ethbtc\n" +
            "btceurs\n" +
            "eosusd\n" +
            "ltceurs\n" +
            "usdteur\n" +
            "eurusd\n" +
            "ethusdt\n")
    @GetMapping("bars/{instrument}/{startTime}/{endTime}")
    public List<BarDTO> getBarsInPeriod(
            @PathVariable String instrument,
            @PathVariable long startTime,
            @PathVariable long endTime
    ) throws IOException {
        return quotesProviderService.getBarsInPeriod(instrument, startTime, endTime);
    }

    @GetMapping("ticker/{instrument}")
    public TickerDTO getActualData(@PathVariable String instrument) throws IOException {
        return quotesProviderService.getTicker(instrument);
    }
}
