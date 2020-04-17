package dsx.bcv.server.controllers;

import dsx.bcv.server.data.models.Trade;
import dsx.bcv.server.security.JwtTokenProvider;
import dsx.bcv.server.services.data_services.PortfolioService;
import dsx.bcv.server.services.data_services.TradeService;
import dsx.bcv.server.services.data_services.UserService;
import dsx.bcv.server.services.parsers.data_formats.CsvFileFormat;
import dsx.bcv.server.views.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("trades")
@Slf4j
public class TradeController {

    private final TradeService tradeService;
    private final UserService userService;
    private final PortfolioService portfolioService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ConversionService conversionService;

    public TradeController(
            TradeService tradeService,
            UserService userService,
            PortfolioService portfolioService,
            JwtTokenProvider jwtTokenProvider,
            ConversionService conversionService
    ) {
        this.tradeService = tradeService;
        this.userService = userService;
        this.portfolioService = portfolioService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.conversionService = conversionService;
    }

    @GetMapping
    public List<TradeVO> getTrades(
            @RequestParam long portfolioId,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(removePrefixFromToken(authorization)),
                portfolioId
        );
        var trades = portfolioService.getTrades(portfolioId);
        return trades.stream()
                .map(trade -> conversionService.convert(trade, TradeVO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("uploadFile")
    public void uploadFile(
            @RequestParam long portfolioId,
            @RequestParam("file") MultipartFile file,
            @RequestParam CsvFileFormat csvFileFormat,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(removePrefixFromToken(authorization)),
                portfolioId
        );
        portfolioService.uploadTradeFile(file, csvFileFormat, portfolioId);
    }

    @GetMapping("{id}")
    public TradeVO findByID(
            @PathVariable long id,
            @RequestParam long portfolioId,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(removePrefixFromToken(authorization)),
                portfolioId
        );
        var trade = portfolioService.findTradeById(id, portfolioId);
        return conversionService.convert(trade, TradeVO.class);
    }

    @PostMapping
    public TradeVO add(
            @RequestBody TradeVO tradeVO,
            @RequestParam long portfolioId,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(removePrefixFromToken(authorization)),
                portfolioId
        );
        var trade = conversionService.convert(tradeVO, Trade.class);
        assert trade != null;
        return conversionService.convert(
                portfolioService.addTrade(trade, portfolioId),
                TradeVO.class
        );
    }

    @PutMapping("{id}")
    public TradeVO update(@PathVariable long id, @RequestBody TradeVO tradeVO) {
        throw new UnsupportedOperationException();
//        log.info(
//                "Request received. Url: {}",
//                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
//        );
//        var trade = conversionService.convert(tradeVO, Trade.class);
//        var newTrade = tradeService.updateById(id, trade);
//        return conversionService.convert(newTrade, TradeVO.class);
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable long id,
            @RequestParam long portfolioId,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(removePrefixFromToken(authorization)),
                portfolioId
        );
        portfolioService.deleteTradeById(id, portfolioId);
    }

    private String removePrefixFromToken(String authorization) {
        return jwtTokenProvider.removePrefixFromToken(authorization);
    }

    private String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsername(token);
    }
}
