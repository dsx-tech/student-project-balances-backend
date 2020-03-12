package dsx.bcv.marketdata_provider.services;

import dsx.bcv.marketdata_provider.data.models.Asset;
import dsx.bcv.marketdata_provider.data.models.Bar;
import dsx.bcv.marketdata_provider.data.repositories.BarRepository;
import dsx.bcv.marketdata_provider.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarService {

    private final BarRepository barRepository;
    private final AssetService assetService;

    public BarService(BarRepository barRepository, AssetService assetService) {
        this.barRepository = barRepository;
        this.assetService = assetService;
    }

    public Bar save(Bar bar) {
        bar.setBaseAsset(
                assetService.findByCode(bar.getBaseAsset().getCode()).orElseThrow(NotFoundException::new)
        );
        return barRepository.save(bar);
    }

    public Iterable<Bar> saveAll(Iterable<Bar> bars) {
        bars.forEach(bar -> bar.setBaseAsset(
                assetService.findByCode(bar.getBaseAsset().getCode()).orElseThrow(NotFoundException::new)
        ));
        return barRepository.saveAll(bars);
    }

    public boolean existsByCurrency(Asset asset) {
        return barRepository.findByBaseCurrency(
                assetService.findByCode(asset.getCode()).orElseThrow(NotFoundException::new)
        ).isPresent();
    }

    public List<Bar> findByBaseCurrencyAndTimestampBetween(Asset asset, long startTime, long endTime) {
        return barRepository.findByBaseCurrencyAndTimestampBetween(asset, startTime, endTime);
    }

    public Bar findTopByBaseCurrencyOrderByTimestampDesc(Asset asset) {
        return barRepository.findTopByBaseCurrencyOrderByTimestampDesc(asset).orElseThrow(NotFoundException::new);
    }
}
