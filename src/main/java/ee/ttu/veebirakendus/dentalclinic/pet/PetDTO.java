package ee.ttu.veebirakendus.dentalclinic.pet;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class PetDTO {
    private Integer id;
    private String name;
    private Integer age;
}
