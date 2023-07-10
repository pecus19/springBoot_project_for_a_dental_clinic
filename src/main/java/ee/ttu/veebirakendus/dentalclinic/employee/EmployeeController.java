package ee.ttu.veebirakendus.dentalclinic.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081", "http://iti0302dentalclinic.hopto.org/", "http://193.40.255.20/"})
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping(value = "/employee", params = "id")
    public EmployeeDTO getEmployeeById(@RequestParam("id") int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping(value = "/employee/personal_code", params = "personalCode")
    public EmployeeDTO getEmployeeByPersonalCode(@RequestParam("personalCode") long personalCode) {
        return employeeService.getEmployeeByPersonalCode(personalCode);
    }

    @GetMapping("/employee/sort")
    public List<EmployeeDTO> getSortedBy(@RequestParam int page, @RequestParam String orderBy) {
        return employeeService.getSortedBy(page, orderBy);
    }

    @GetMapping("/employee/search")
    public List<EmployeeDTO> getSearch(int page, String orderBy, String order, String firstName, String lastName) {
        return employeeService.search(new EmployeeFilter(page, orderBy, order, firstName, lastName));
    }

    @PostMapping("/employee")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    @PutMapping("/employee")
    public EmployeeDTO update(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.putEmployee(employeeDTO);
    }

    @DeleteMapping("/employee")
    public ResponseEntity<String> delete(@RequestParam int id) {
        return employeeService.deleteEmployeeById(id);
    }
}
