package ee.ttu.veebirakendus.dentalclinic.client;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Long personalCode;
    private String doctorName;
}
