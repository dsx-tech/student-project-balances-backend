package dsx.bcv.marketdata_provider.controllers;

import dsx.bcv.marketdata_provider.services.QuoteProviderService;
import dsx.bcv.marketdata_provider.views.BarVO;
import dsx.bcv.marketdata_provider.views.TickerVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quotes")
public class QuotesProviderController {

    private final QuoteProviderService quoteProviderService;

    public QuotesProviderController(QuoteProviderService quoteProviderService) {
        this.quoteProviderService = quoteProviderService;
    }

    @ApiOperation("Get bars for every day from startTime to endTime.\n" +
            "StartTime & endTime are Unix Timestamps in seconds (https://www.epochconverter.com).\n" +
            "Instrument example: eur-rub.\n" +
            "Supported currencies:\n" + "usd\n" + "bsv\n" + "bch\n" + "eurs\n" + "eos\n" + "btc\n" +
            "xrp\n" + "btg\n" + "gbp\n" + "eth\n" + "ltc\n" + "try\n" + "rub\n" + "eur\n" + "usdt\n")
    @GetMapping("bars/{instrument}/{startTime}/{endTime}")
    public List<BarVO> getBarsInPeriod(
            @PathVariable String instrument,
            @PathVariable long startTime,
            @PathVariable long endTime
    ) {
        return quoteProviderService.getBarsInPeriod(instrument, startTime, endTime);
    }

    @ApiOperation("Get ticker.\n" +
            "Instrument example: eur-rub.\n" +
            "Supported currencies:\n" + "usd\n" + "bsv\n" + "bch\n" + "eurs\n" + "eos\n" + "btc\n" +
            "xrp\n" + "btg\n" + "gbp\n" + "eth\n" + "ltc\n" + "try\n" + "rub\n" + "eur\n" + "usdt\n")
    @GetMapping("ticker/{instrument}")
    public TickerVO getTicker(@PathVariable String instrument) {
        return quoteProviderService.getTicker(instrument);
    }
}
