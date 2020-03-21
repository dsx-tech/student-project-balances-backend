package dsx.bcv.server.data.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "currencies")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
