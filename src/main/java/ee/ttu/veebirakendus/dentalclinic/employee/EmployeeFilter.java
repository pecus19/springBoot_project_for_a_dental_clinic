package ee.ttu.veebirakendus.dentalclinic.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeFilter {
    private Integer page;
    private String orderBy;
    private String order;
    private String firstName;
    private String lastName;

    public int getFirstResult() {
        return page * 10;
    }
}

