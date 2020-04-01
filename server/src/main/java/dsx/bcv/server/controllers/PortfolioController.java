package dsx.bcv.server.controllers;

import dsx.bcv.server.data.models.Portfolio;
import dsx.bcv.server.security.JwtTokenProvider;
import dsx.bcv.server.services.data_services.PortfolioService;
import dsx.bcv.server.services.data_services.UserService;
import dsx.bcv.server.views.PortfolioVO;
import dsx.bcv.server.views.TradeVO;
import dsx.bcv.server.views.TransactionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("portfolios")
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserService userService;
    private final ConversionService conversionService;
    private final JwtTokenProvider jwtTokenProvider;

    public PortfolioController(
            PortfolioService portfolioService,
            UserService userService,
            ConversionService conversionService,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.portfolioService = portfolioService;
        this.userService = userService;
        this.conversionService = conversionService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public List<PortfolioVO> findAll(@RequestHeader("Authorization") String authorization) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        return userService.findAllPortfoliosByUsername(getUsernameFromToken(getToken(authorization))).stream()
                .map(portfolio -> conversionService.convert(portfolio, PortfolioVO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public PortfolioVO findByID(
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = userService.findPortfolioByUsernameAndPortfolioId(
                getUsernameFromToken(getToken(authorization)),
                id
        );
        return conversionService.convert(portfolio, PortfolioVO.class);
    }

    @GetMapping("by_name/{name}")
    public PortfolioVO findByName(
            @PathVariable String name,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = userService.findPortfolioByUsernameAndPortfolioName(
                getUsernameFromToken(getToken(authorization)),
                name);
        return conversionService.convert(portfolio, PortfolioVO.class);
    }

    @PostMapping
    public PortfolioVO add(
            @RequestBody PortfolioVO portfolioVO,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = conversionService.convert(portfolioVO, Portfolio.class);
        assert portfolio != null;
        return conversionService.convert(
                userService.addPortfolioByUsername(
                        getUsernameFromToken(getToken(authorization)),
                        portfolio
                ),
                PortfolioVO.class
        );
    }

    @PutMapping("{id}")
    public PortfolioVO updateById(
            @PathVariable long id,
            @RequestBody PortfolioVO portfolioVO,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        var portfolio = conversionService.convert(portfolioVO, Portfolio.class);
        var newPortfolio = userService.updatePortfolioByUsernameAndPortfolioId(
                getUsernameFromToken(getToken(authorization)),
                id,
                portfolio
        );
        return conversionService.convert(newPortfolio, PortfolioVO.class);

    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.deletePortfolioByUsernameAndPortfolioId(getUsernameFromToken(getToken(authorization)), id);
    }

    @GetMapping("{id}/trades")
    public List<TradeVO> getTradesByPortfolioId(
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(getToken(authorization)),
                id
        );
        var trades = portfolioService.getTradesByPortfolioId(id);
        return trades.stream()
                .map(trade -> conversionService.convert(trade, TradeVO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/transactions")
    public List<TransactionVO> getTransactionsByPortfolioId(
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info(
                "Request received. Url: {}",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString()
        );
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(getToken(authorization)),
                id
        );
        var transactions = portfolioService.getTransactionsByPortfolioId(id);
        return transactions.stream()
                .map(transaction -> conversionService.convert(transaction, TransactionVO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("{id}/trades/upload")
    public void uploadTradeFileByPortfolioId(
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization,
            @RequestParam("file") MultipartFile file
    ) {
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(getToken(authorization)),
                id
        );
        portfolioService.uploadTradeFileByPortfolioId(file, id);
    }

    @PostMapping("{id}/transactions/upload")
    public void uploadTransactionFileByPortfolioId(
            @PathVariable long id,
            @RequestHeader("Authorization") String authorization,
            @RequestParam("file") MultipartFile file
    ) {
        userService.ThrowNotFoundIfUserDoesntHavePortfolioWithId(
                getUsernameFromToken(getToken(authorization)),
                id
        );
        portfolioService.uploadTransactionFileByPortfolioId(file, id);
    }

    private String getToken(String authorization) {
        return authorization.substring("Token_".length());
    }

    private String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsername(token);
    }
}
