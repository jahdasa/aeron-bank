package gc.garcol.bankcluster.domain.portfolio;

import lombok.Data;

@Data
public class Portfolio {
    private long id;
    private boolean active;

    public Portfolio(long id) {
        this.id = id;
        this.active = true;
    }
}
