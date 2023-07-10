package ee.ttu.veebirakendus.dentalclinic.pet;

import ee.ttu.veebirakendus.dentalclinic.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {

//    Optional<Pet> findByPersonalCode(Integer id);
//
//    Pet findFirstByFirstName(String name);
}
