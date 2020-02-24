package dsx.bcv.marketdata_provider.services.quote_providers.alpha_vantage.models;

import lombok.Data;

@Data
public class AlphaVantageCurrency {

    private String code;
    private String name;

    public AlphaVantageCurrency(String code, String name) {
        this.code = code.toLowerCase();
        this.name = name.toLowerCase();
    }

    @Override
    public String toString() {
        return code;
    }
}
