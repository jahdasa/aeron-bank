package gc.garcol.bankcluster.domain.portfolio;

import lombok.Data;
import org.agrona.collections.Object2LongHashMap;

@Data
public class Portfolio
{
    private static final int MISSING_VALUE = 0;

    private long id;
    private boolean active;

    final Object2LongHashMap<String> totalAssets = new Object2LongHashMap<>(MISSING_VALUE);
    final Object2LongHashMap<String> blockedAssets = new Object2LongHashMap<>(MISSING_VALUE);

    public Portfolio(long id) {
        this.id = id;
        this.active = true;
    }

    public void set(final String isin, final long quantity, final long blockedQuantity)
    {
        totalAssets.put(isin, quantity);
        blockedAssets.put(isin, blockedQuantity);
    }

    public void remove(final String isin)
    {
        totalAssets.remove(isin);
        blockedAssets.remove(isin);
    }

    public boolean block(final String isin, final long quantity)
    {
        if (totalAssets.getValue(isin) - blockedAssets.getValue(isin) - quantity >= 0)
        {
            blockedAssets.put(isin, blockedAssets.getValue(isin) + quantity);
            return true;
        }
        return false;
    }

    public boolean unblock(final String isin, final long quantity)
    {
        if(blockedAssets.getValue(isin) >= quantity)
        {
            blockedAssets.put(isin, blockedAssets.getValue(isin) - quantity);
            return true;
        }
        return false;
    }

    public boolean increase(final String isin, final long quantity)
    {
        totalAssets.put(isin, totalAssets.getValue(isin) + quantity);
        return true;
    }

    public boolean decrease(final String isin, final long quantity)
    {
        if(totalAssets.getValue(isin) >= quantity)
        {
            totalAssets.put(isin, totalAssets.getValue(isin) - quantity);
            return true;
        }
        return false;
    }
}
