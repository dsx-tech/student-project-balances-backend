package dsx.bcv.data.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Trade {

    public Trade(LocalDateTime dateTime, String instrument, String tradeType, BigDecimal tradedQuantity,
                 String tradedQuantityCurrency, BigDecimal tradedPrice, String tradedPriceCurrency,
                 BigDecimal commission, String commissionCurrency, long tradeValueId) {
        this.dateTime   = dateTime;
        this.instrument = instrument;
        this.tradeType = tradeType;
        this.tradedQuantity = tradedQuantity;
        this.tradedQuantityCurrency = tradedQuantityCurrency;
        this.tradedPrice = tradedPrice;
        this.tradedPriceCurrency = tradedPriceCurrency;
        this.commission = commission;
        this.commissionCurrency = commissionCurrency;
        this.tradeValueId = tradeValueId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getTradeType() {
        return tradeType;
    }

    public BigDecimal getTradedQuantity() {
        return tradedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return tradeValueId == trade.tradeValueId &&
                dateTime.equals(trade.dateTime) &&
                instrument.equals(trade.instrument) &&
                tradeType.equals(trade.tradeType) &&
                tradedQuantity.equals(trade.tradedQuantity) &&
                tradedQuantityCurrency.equals(trade.tradedQuantityCurrency) &&
                tradedPrice.equals(trade.tradedPrice) &&
                tradedPriceCurrency.equals(trade.tradedPriceCurrency) &&
                commission.equals(trade.commission) &&
                commissionCurrency.equals(trade.commissionCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, instrument, tradeType, tradedQuantity, tradedQuantityCurrency, tradedPrice, tradedPriceCurrency, commission, commissionCurrency, tradeValueId);
    }

    public String getTradedQuantityCurrency() {
        return tradedQuantityCurrency;
    }

    public BigDecimal getTradedPrice() {
        return tradedPrice;
    }

    public String getTradedPriceCurrency() {
        return tradedPriceCurrency;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    public long getTradeValueId() {
        return tradeValueId;
    }

    private final LocalDateTime dateTime;
    private final String instrument;
    private final String tradeType;
    private final BigDecimal tradedQuantity;
    private final String tradedQuantityCurrency;
    private final BigDecimal tradedPrice;
    private final String tradedPriceCurrency;
    private final BigDecimal commission;
    private final String commissionCurrency;
    private final long tradeValueId;
}
