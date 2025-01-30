package gc.garcol.bankcluster.domain.portfolio;

import gc.garcol.bankcluster.domain.SessionMessageContext;
import gc.garcol.bankcluster.domain.TimerManager;
import gc.garcol.bankcluster.domain.account.AccountClusterClientResponder;
import gc.garcol.bankcluster.domain.account.AccountResponseCode;
import gc.garcol.common.exception.Bank4xxException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.agrona.collections.Long2ObjectHashMap;
import org.agrona.collections.MutableLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thaivc
 * @since 2024
 */
@RequiredArgsConstructor
public class Portfolios implements PortfolioUseCase, PortfolioRestorable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Portfolios.class);

    @Getter
    private final Long2ObjectHashMap<Portfolio> portfolios = new Long2ObjectHashMap<>();

    @Getter
    private final MutableLong idGenerator = new MutableLong(1);

    // Injected dependencies
    private final SessionMessageContext context;
    private final AccountClusterClientResponder accountClusterClientResponder;
    private final TimerManager timerManager;

    @Override
    public void openPortfolio(String correlationId) {
        LOGGER.debug("[Start] Create portfolio: (correlationId: {})", correlationId);
        long portfolioId = idGenerator.getAndIncrement();
        portfolios.put(portfolioId, new Portfolio(portfolioId));
        accountClusterClientResponder.onAccountAdded(
            correlationId, portfolioId, AccountResponseCode.ADD_PORTFOLIO_SUCCESS
        );
        LOGGER.debug("[End] Create portfolio: (correlationId: {})", correlationId);
    }

    @Override
    public void set(
        final String correlationId,
        final long portfolioId,
        final String isin,
        final long quantity,
        final long blockedQuantity)
    {
        LOGGER.debug(
            "[Start] Withdraw account: (correlationId: {}, accountId: {}, amount: {}, blockedQuantity: {})",
            correlationId,
            portfolioId,
            quantity,
            blockedQuantity);

        if (!portfolios.containsKey(portfolioId))
        {
            LOGGER.warn("portfolio {} does not exist", portfolioId);
            accountClusterClientResponder.rejectWithdrawAccount(
                    correlationId, portfolioId, amount, AccountResponseCode.ACCOUNT_NOT_EXISTED
            );
            throw new Bank4xxException(String.format("Withdrawn account %s does not exist", accountId));
        }
        if (accounts.get(accountId).getAmount() < amount) {
            LOGGER.warn("Withdrawn account {} does not have enough balance", accountId);
            accountClusterClientResponder.rejectWithdrawAccount(
                    correlationId, accountId, amount, AccountResponseCode.BALANCE_NOT_ENOUGH
            );
            throw new Bank4xxException(String.format("Withdrawn account %s does not have enough balance", accountId));
        }
        accounts.get(accountId).decrease(amount);
        accountClusterClientResponder.onAccountWithdrawn(
                correlationId, accountId, amount, accounts.get(accountId).getAmount(), AccountResponseCode.WITHDRAW_ACCOUNT_SUCCESS
        );
        LOGGER.debug("[End] Withdraw account: (correlationId: {}, accountId: {}, amount: {})", correlationId, accountId, amount);
    }

    @Override
    public void restorePortfolio(long portfolioId,boolean active) {
        final Portfolio portfolio = new Portfolio(portfolioId);
        portfolio.setActive(active);
        portfolios.put(portfolioId, portfolio);
    }

    @Override
    public void restoreAutoIdGenerator(final long portfolioId) {
        LOGGER.info("Restoring auto id generator to {}", portfolioId);
        idGenerator.set(portfolioId);
    }
}
