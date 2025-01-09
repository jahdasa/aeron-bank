package gc.garcol.bankcluster.domain.portfolio;

/**
 * @author thaivc
 * @since 2024
 */
public interface PortfolioRestorable {

    /**
     * Loads an portfolio from the snapshot
     *
     * @param portfolioId
     * @param active
     */
    void restorePortfolio(long portfolioId, boolean active);

    /**
     * Restores the account id generator from snapshot
     *
     * @param portfolioId the auction id
     */
    void restoreAutoIdGenerator(final long portfolioId);
}
