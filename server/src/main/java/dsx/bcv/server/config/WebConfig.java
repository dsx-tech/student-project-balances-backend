package dsx.bcv.server.config;

import dsx.bcv.server.converters.CurrencyToCurrencyVOConverter;
import dsx.bcv.server.converters.InstrumentToInstrumentVOConverter;
import dsx.bcv.server.converters.TradeToTradeVOConverter;
import dsx.bcv.server.converters.TransactionToTransactionVOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CurrencyToCurrencyVOConverter());
        registry.addConverter(new InstrumentToInstrumentVOConverter());
        registry.addConverter(new TradeToTradeVOConverter());
        registry.addConverter(new TransactionToTransactionVOConverter());
    }
}
