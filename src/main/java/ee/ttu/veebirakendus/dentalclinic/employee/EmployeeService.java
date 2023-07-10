package ee.ttu.veebirakendus.dentalclinic.employee;

import ee.ttu.veebirakendus.dentalclinic.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private static final int PAGE_SIZE = 2;

    private final EmployeeCriteriaRepository employeeCriteriaRepository;

    private Employee findById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id %d was not found.".formatted(id)));
    }

    private Employee findByPersonalCode(long personalCode) {
        return employeeRepository.findByPersonalCode(personalCode)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with personal code %d was not found.".formatted(personalCode)));
    }

    public List<EmployeeDTO> findAll() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }

    public EmployeeDTO getEmployeeById(int id) {
        return employeeMapper.toDto(findById(id));
    }

    public EmployeeDTO getEmployeeByPersonalCode(long personalCode) {
        return employeeMapper.toDto(findByPersonalCode(personalCode));
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        return employeeMapper.toDto(employeeRepository.save(employeeMapper.fromDto(employeeDTO)));
    }

    public EmployeeDTO putEmployee(EmployeeDTO employeeDTO) {
        return employeeMapper.toDto(employeeRepository.save(employeeMapper.fromDto(employeeDTO)));
    }

    public ResponseEntity<String> deleteEmployeeById(int id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        return new ResponseEntity<>("Employee was successfully deleted.", HttpStatus.OK);
    }

    public Page<Employee> getEmployee(int page, Sort sort) {
        return employeeRepository.findAll(PageRequest.of(page, PAGE_SIZE, sort));
    }

    public List<EmployeeDTO> getSortedBy(int page, String orderBy) {
        Sort sort = Sort.by(orderBy).ascending();
        return employeeMapper.toDtoList(getEmployee(page, sort).getContent());
    }

    public List<EmployeeDTO> search(EmployeeFilter employeeFilter) {
        var employeeList = employeeCriteriaRepository.searchCount(employeeFilter);
        return employeeMapper.toDtoList(employeeList);
    }
}
