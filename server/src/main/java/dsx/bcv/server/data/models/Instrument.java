package dsx.bcv.server.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "instruments")
@Table(uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"base_currency", "quoted_currency"})
})
@Data
@NoArgsConstructor
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    //@Column(name = "base_currency")
    private Currency baseCurrency;
    @ManyToOne
    //@Column(name = "quoted_currency")
    private Currency quotedCurrency;

    public Instrument(Currency baseCurrency, Currency quotedCurrency) {
        this.baseCurrency = baseCurrency;
        this.quotedCurrency = quotedCurrency;
    }

    public Instrument(String instrument) {

//        var currencyPair = Lists.newArrayList(Splitter.on("-").split(instrument));
//        if (currencyPair.size() != 2) {
//            throw new NotFoundException("Invalid instrument");
//        }
//
//        var baseCurrency = new Currency(currencyPair.get(0));
//        var quotedCurrency = new Currency(currencyPair.get(1));

        var baseCurrency = new Currency(instrument.substring(0, 3));
        var quotedCurrency = new Currency(instrument.substring(3, 6));

        this.baseCurrency = baseCurrency;
        this.quotedCurrency = quotedCurrency;
    }
}
