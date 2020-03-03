package dsx.bcv.marketdata_provider.config;

import dsx.bcv.marketdata_provider.converters.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DsxBarToBarConverter());
        registry.addConverter(new DsxTickerToTickerConverter());
        registry.addConverter(new BarToBarVOConverter());
        registry.addConverter(new TickerToTickerVOConverter());
        registry.addConverter(new DsxInstrumentEdgeToInstrumentConverter());
        registry.addConverter(new InstrumentToInstrumentVOConverter());
        registry.addConverter(new AlphaVantageForexBarToBarConverter());
        registry.addConverter(new AlphaVantageCryptoBarToBarConverter());
        registry.addConverter(new AlphaVantageCurrencyToCurrencyConverter());
        registry.addConverter(new CurrencyToAlphaVantageCurrencyConverter());
        registry.addConverter(new CurrencyToCurrencyVOConverter());
        registry.addConverter(new AlphaVantageTickerToTickerConverter());
    }
}
