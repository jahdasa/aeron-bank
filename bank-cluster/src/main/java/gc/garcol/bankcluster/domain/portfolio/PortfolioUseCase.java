package gc.garcol.bankcluster.domain.portfolio;

/**
 * @author thaivc
 * @since 2024
 */
public interface PortfolioUseCase {
    void openPortfolio(String correlationId);

    void set(String correlationId, String isin, long quantity);

}
