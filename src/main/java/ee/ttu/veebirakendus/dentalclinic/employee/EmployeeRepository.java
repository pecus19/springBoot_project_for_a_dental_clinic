package ee.ttu.veebirakendus.dentalclinic.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByPersonalCode(Long personalCode);

    Employee findFirstByFirstName(String name);
}
