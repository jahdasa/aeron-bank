package gc.garcol.bankapp.app;

import gc.garcol.bankapp.service.AccountClusterEgressListener;
import gc.garcol.bankapp.service.AccountCommandDispatcher;
import gc.garcol.bankapp.service.AccountCommandDispatcherImpl;
import gc.garcol.bankapp.service.AccountCommandHandlerImpl;
import gc.garcol.bankapp.service.portfolio.PortfolioCommandDispatcher;
import gc.garcol.bankapp.service.portfolio.PortfolioCommandDispatcherImpl;
import org.agrona.BufferUtil;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.SleepingMillisIdleStrategy;
import org.agrona.concurrent.UnsafeBuffer;
import org.agrona.concurrent.ringbuffer.ManyToOneRingBuffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.agrona.concurrent.ringbuffer.RingBufferDescriptor.TRAILER_LENGTH;

@Configuration
public class AccountDomainSetup {

    @Bean
    public IdleStrategy commandIdleStrategy() {
        return new SleepingMillisIdleStrategy();
    }

    @Bean
    public ManyToOneRingBuffer commandBuffer() {
        final var adminClusterBuffer =
            new UnsafeBuffer(BufferUtil.allocateDirectAligned((1 << 15) + TRAILER_LENGTH, 8));
        return new ManyToOneRingBuffer(adminClusterBuffer);
    }

    @Bean
    public AccountCommandDispatcher accountCommandDispatcher(ManyToOneRingBuffer commandBuffer) {
        var accountCommandDispatcher = new AccountCommandDispatcherImpl();
        accountCommandDispatcher.setCommandBuffer(commandBuffer);
        return accountCommandDispatcher;
    }

    @Bean
    public PortfolioCommandDispatcher portfolioCommandDispatcher(ManyToOneRingBuffer commandBuffer)
    {
        var dispatcher = new PortfolioCommandDispatcherImpl();
        dispatcher.setCommandBuffer(commandBuffer);

        return dispatcher;
    }


    @Bean
    public AccountCommandHandlerImpl accountCommandHandler(
        final ManyToOneRingBuffer commandBuffer,
        final AccountClusterEgressListener egressListener,
        final ClusterVariable clusterVariable
    ) {
        var accountCommandHandler = new AccountCommandHandlerImpl();
        accountCommandHandler.setCommandBuffer(commandBuffer);
        accountCommandHandler.setEgressListener(egressListener);
        accountCommandHandler.setClusterVariable(clusterVariable);
        return accountCommandHandler;
    }
}
