package gc.garcol.bankapp.service.portfolio;

import gc.garcol.bankapp.transport.api.account.command.CreateAccountCommand;
import gc.garcol.bankapp.transport.api.account.command.DepositAccountCommand;
import gc.garcol.bankapp.transport.api.account.command.TransferBalanceCommand;
import gc.garcol.bankapp.transport.api.account.command.WithdrawAccountCommand;
import gc.garcol.bankapp.transport.api.portfolio.command.dto.CreatePortfolioCommandInput;

/**
 * @author thaivc
 * @since 2024
 */
public interface PortfolioCommandDispatcher {
    void createPortfolio(CreatePortfolioCommandInput input);
}
