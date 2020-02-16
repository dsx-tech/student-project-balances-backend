package dsx.bcv.marketdata_provider.config;

import dsx.bcv.marketdata_provider.converters.BarToBarVOConverter;
import dsx.bcv.marketdata_provider.converters.DsxBarToBarConverter;
import dsx.bcv.marketdata_provider.converters.DsxTickerToTickerConverter;
import dsx.bcv.marketdata_provider.converters.TickerToTickerVOConverter;
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
    }
}
