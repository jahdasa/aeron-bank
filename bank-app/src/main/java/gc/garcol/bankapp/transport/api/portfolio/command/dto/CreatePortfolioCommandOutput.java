package gc.garcol.bankapp.transport.api.portfolio.command.dto;

import gc.garcol.bankapp.transport.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreatePortfolioCommandOutput extends BaseResponse{
    private String correlationId;
    private long portfolioId;
}
