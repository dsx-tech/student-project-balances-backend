package dsx.bcv.marketdata_provider.views;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyVO {
    private String code;
    private String name;
}
