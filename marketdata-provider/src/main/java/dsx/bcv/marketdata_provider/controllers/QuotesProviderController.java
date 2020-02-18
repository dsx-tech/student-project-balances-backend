package dsx.bcv.marketdata_provider.controllers;

import dsx.bcv.marketdata_provider.services.QuoteProviderService;
import dsx.bcv.marketdata_provider.views.BarVO;
import dsx.bcv.marketdata_provider.views.InstrumentVO;
import dsx.bcv.marketdata_provider.views.TickerVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("quotes")
@Slf4j
public class QuotesProviderController {

    private final QuoteProviderService quoteProviderService;
    private final String instrumentExample = "Instrument example: eur-rub.\n";
    private final String supportedCurrencies = "Supported currencies:\n" +
            "usd\n" + "bsv\n" + "bch\n" + "eurs\n" + "eos\n" +
            "btc\n" + "xrp\n" + "btg\n" + "gbp\n"  + "eth\n" +
            "ltc\n" + "try\n" + "rub\n" + "eur\n"  + "usdt\n";

    public QuotesProviderController(QuoteProviderService quoteProviderService) {
        this.quoteProviderService = quoteProviderService;
    }

    @ApiOperation("Get supported instruments")
    @GetMapping("instruments")
    public List<InstrumentVO> getInstruments() {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return quoteProviderService.getInstruments();
    }

    @ApiOperation("Get bars for every day from startTime to endTime.\n" +
            "StartTime & endTime are Unix Timestamps in seconds (https://www.epochconverter.com).\n" +
            instrumentExample + supportedCurrencies)
    @GetMapping("bars/{instrument}/{startTime}/{endTime}")
    public List<BarVO> getBarsInPeriod(
            @PathVariable String instrument,
            @PathVariable long startTime,
            @PathVariable long endTime
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return quoteProviderService.getBarsInPeriod(instrument, startTime, endTime);
    }

    @ApiOperation("Get ticker.\n" + instrumentExample + supportedCurrencies)
    @GetMapping("ticker/{instrument}")
    public TickerVO getTicker(@PathVariable String instrument) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return quoteProviderService.getTicker(instrument);
    }
}
