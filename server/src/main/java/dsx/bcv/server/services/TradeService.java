package dsx.bcv.server.services;

import dsx.bcv.server.data.dto.TradeDTO;
import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.data.models.TradeType;
import dsx.bcv.server.data.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final InstrumentService instrumentService;
    private final CurrencyService currencyService;
    private final LocalDateTimeService localDateTimeService;

    public TradeService(
            TradeRepository tradeRepository,
            InstrumentService instrumentService,
            CurrencyService currencyService,
            LocalDateTimeService localDateTimeService
    ) {
        this.tradeRepository = tradeRepository;
        this.instrumentService = instrumentService;
        this.currencyService = currencyService;
        this.localDateTimeService = localDateTimeService;
    }

    public Trade save(Trade trade) {
        trade.setInstrument(instrumentService.saveIfNotExists(trade.getInstrument()));
        trade.setTradedQuantityCurrency(currencyService.saveIfNotExists(trade.getTradedQuantityCurrency()));
        trade.setTradedPriceCurrency(currencyService.saveIfNotExists(trade.getTradedPriceCurrency()));
        trade.setCommissionCurrency(currencyService.saveIfNotExists(trade.getCommissionCurrency()));
        return tradeRepository.save(trade);
    }

    public Trade getTrade(
            String localDateTime,
            String instrument,
            String tradeType,
            String tradedQuantity,
            String tradedQuantityCurrency,
            String tradedPrice,
            String tradedPriceCurrency,
            String commission,
            String commissionCurrency,
            String tradeValueId
    ) {
        return new Trade(
                localDateTimeService.getDateTimeFromString(localDateTime),
                instrumentService.getInstrument(instrument),
                tradeType.toLowerCase().equals("buy") ? TradeType.Buy : TradeType.Sell,
                new BigDecimal(tradedQuantity.replaceAll(",", ".")),
                currencyService.getCurrency(tradedQuantityCurrency),
                new BigDecimal(tradedPrice.replaceAll(",", ".")),
                currencyService.getCurrency(tradedPriceCurrency),
                new BigDecimal(commission.replaceAll(",", ".")),
                currencyService.getCurrency(commissionCurrency),
                tradeValueId
        );
    }

    public Trade getTrade(TradeDTO tradeDTO) {
        return getTrade(
                tradeDTO.getDateTime().toString(),
                tradeDTO.getInstrument(),
                tradeDTO.getTradeType(),
                tradeDTO.getTradedQuantity().toString(),
                tradeDTO.getTradedQuantityCurrency(),
                tradeDTO.getTradedPrice().toString(),
                tradeDTO.getTradedPriceCurrency(),
                tradeDTO.getCommission().toString(),
                tradeDTO.getCommissionCurrency(),
                tradeDTO.getTradeValueId()
        );
    }
}
