package dsx.bcv.server.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "portfolios")
@Data
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Trade> trades = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    public Portfolio(String name) {
        this.name = name;
    }
}
