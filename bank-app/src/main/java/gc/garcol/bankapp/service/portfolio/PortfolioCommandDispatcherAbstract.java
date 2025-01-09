package gc.garcol.bankapp.service.portfolio;

import gc.garcol.protocol.*;
import lombok.Setter;
import org.agrona.ExpandableArrayBuffer;
import org.agrona.concurrent.ringbuffer.ManyToOneRingBuffer;

/**
 * @author thaivc
 * @since 2024
 */
public abstract class PortfolioCommandDispatcherAbstract implements PortfolioCommandDispatcher {
    @Setter
    protected ManyToOneRingBuffer commandBuffer;

    protected final ExpandableArrayBuffer buffer = new ExpandableArrayBuffer(1 << 10);
    protected final MessageHeaderEncoder messageHeaderEncoder = new MessageHeaderEncoder();
    protected final CreatePortfolioCommandBufferEncoder createPortfolioCommandBufferEncoder = new CreatePortfolioCommandBufferEncoder();
}
