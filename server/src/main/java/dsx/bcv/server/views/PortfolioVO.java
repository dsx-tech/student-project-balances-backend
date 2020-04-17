package dsx.bcv.server.views;

import lombok.Data;

@Data
public class PortfolioVO {

    private long id;
    private String name;
    //private Set<TradeVO> trades;
    //private Set<TransactionVO> transactions;

    public PortfolioVO(long id, String name
            //, Set<TradeVO> trades, Set<TransactionVO> transactions
    ) {
        this.id = id;
        this.name = name;
        //this.trades = trades;
        //this.transactions = transactions;
    }
}
