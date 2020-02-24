package dsx.bcv.marketdata_provider.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Currency {

    private String code;
    private String name;

    public Currency(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
