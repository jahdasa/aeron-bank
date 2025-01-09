package gc.garcol.bankapp.transport.api.portfolio.command.dto;

import gc.garcol.bankapp.transport.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class CreatePortfolioCommandInput extends BaseCommand{
}
