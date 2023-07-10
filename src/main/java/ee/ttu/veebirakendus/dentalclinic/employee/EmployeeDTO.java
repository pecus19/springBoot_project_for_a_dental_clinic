package ee.ttu.veebirakendus.dentalclinic.employee;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class EmployeeDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer height;
    private Integer weight;
    private String sex;
    private String departmentName;
    private Long personalCode;
}





