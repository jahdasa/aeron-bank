package gc.garcol.bankapp.service;

import org.agrona.concurrent.ringbuffer.ManyToOneRingBuffer;
import org.agrona.concurrent.ringbuffer.OneToOneRingBuffer;

/**
 * @author thaivc
 * @since 2024
 */
public interface CommandBufferChannel {
    void setCommandBuffer(ManyToOneRingBuffer commandBuffer);
}
