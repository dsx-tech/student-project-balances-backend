package dsx.bcv.server.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity(name = "instruments")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(exclude = "id")
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Currency firstCurrency;
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Currency secondCurrency;

    @Override
    public String toString() {
        return firstCurrency.toString() + secondCurrency.toString();
    }
}
