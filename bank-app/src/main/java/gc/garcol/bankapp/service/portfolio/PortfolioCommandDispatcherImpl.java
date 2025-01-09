package gc.garcol.bankapp.service.portfolio;

import gc.garcol.bankapp.service.CommandBufferChannel;
import gc.garcol.bankapp.service.constants.ServerConstant;
import gc.garcol.bankapp.transport.api.portfolio.command.dto.CreatePortfolioCommandInput;
import gc.garcol.common.exception.Bank5xxException;
import gc.garcol.protocol.MessageHeaderEncoder;
import org.agrona.ExpandableArrayBuffer;

public class PortfolioCommandDispatcherImpl extends PortfolioCommandDispatcherAbstract implements CommandBufferChannel {

    @Override
    public void createPortfolio(CreatePortfolioCommandInput command)
    {
        createPortfolioCommandBufferEncoder.wrapAndApplyHeader(buffer, 0, messageHeaderEncoder);
        createPortfolioCommandBufferEncoder.correlationId(command.getCorrelationId());
        offerRingBufferMessage(buffer, 0,
            MessageHeaderEncoder.ENCODED_LENGTH + createPortfolioCommandBufferEncoder.encodedLength());
    }

    private void offerRingBufferMessage(
        final ExpandableArrayBuffer buffer,
        final int offset,
        final int encodedLength)
    {
        final boolean success = commandBuffer.write(ServerConstant.MESSAGE_TYPE_ID, buffer, offset, encodedLength);
        if (!success) {
            throw new Bank5xxException("Failed to write to command buffer");
        }
    }
}
