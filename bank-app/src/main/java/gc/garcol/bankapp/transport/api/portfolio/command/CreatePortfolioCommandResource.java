package gc.garcol.bankapp.transport.api.portfolio.command;

import gc.garcol.apputil.observation.TracingUtil;
import gc.garcol.bankapp.service.SimpleAccountRequestReplyFuture;
import gc.garcol.bankapp.service.portfolio.PortfolioCommandDispatcher;
import gc.garcol.bankapp.transport.ResponseWrapper;
import gc.garcol.bankapp.transport.api.portfolio.command.dto.CreatePortfolioCommandInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class CreatePortfolioCommandResource {

    private final PortfolioCommandDispatcher portfolioCommandDispatcher;
    private final SimpleAccountRequestReplyFuture requestReplyFuture;

    @PostMapping("/create")
    public CompletableFuture<ResponseWrapper> createAccount() {
        TracingUtil.startNewSpan("create");
        var input = new CreatePortfolioCommandInput();
        log.debug("Creating portfolio with : {}", input);
        return requestReplyFuture.request(input.getCorrelationId(),
                () -> portfolioCommandDispatcher.createPortfolio(input)
        );
    }
}
