package dsx.bcv.marketdata_provider.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Instrument {

    public Asset baseAsset;
    public Asset quotedAsset;

    @Override
    public String toString() {
        return baseAsset + "-" + quotedAsset;
    }
}
