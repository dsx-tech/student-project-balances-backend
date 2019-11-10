package dsx.bcv.data.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

public class Deal {

    public Deal(LocalDateTime dateTime, String instrument, String dealType, BigDecimal tradedQuantity,
                String tradedQuantityCurrency, BigDecimal tradedPrice, String tradedPriceCurrency,
                BigDecimal commission, String commissionCurrency, BigInteger tradeValueId) {
        this.dateTime   = dateTime;
        this.instrument = instrument;
        this.dealType   = dealType;
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

    public String getDealType() {
        return dealType;
    }
    
    public BigDecimal getTradedQuantity() {
        return tradedQuantity;
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

    public BigInteger getTradeValueId() {
        return tradeValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return dateTime.equals(deal.dateTime) &&
                instrument.equals(deal.instrument) &&
                dealType.equals(deal.dealType) &&
                tradedQuantity.toString().equals(deal.tradedQuantity.toString()) &&
                tradedQuantityCurrency.equals(deal.tradedQuantityCurrency) &&
                tradedPrice.toString().equals(deal.tradedPrice.toString()) &&
                tradedPriceCurrency.equals(deal.tradedPriceCurrency) &&
                commission.toString().equals(deal.commission.toString()) &&
                commissionCurrency.equals(deal.commissionCurrency) &&
                tradeValueId.equals(deal.tradeValueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, instrument, dealType, tradedQuantity.toString(), tradedQuantityCurrency,
                tradedPrice.toString(), tradedPriceCurrency, commission.toString(), commissionCurrency, tradeValueId);
    }

    private final LocalDateTime dateTime;
    private final String instrument;
    private final String dealType;
    private final BigDecimal tradedQuantity;
    private final String tradedQuantityCurrency;
    private final BigDecimal tradedPrice;
    private final String tradedPriceCurrency;
    private final BigDecimal commission;
    private final String commissionCurrency;
    private final BigInteger tradeValueId;
}
