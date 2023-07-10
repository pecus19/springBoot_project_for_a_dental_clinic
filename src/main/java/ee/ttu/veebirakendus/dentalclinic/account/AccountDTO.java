package ee.ttu.veebirakendus.dentalclinic.account;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class AccountDTO {
    private Integer id;
    private String email;
    private String plainPassword;
}
