package ee.ttu.veebirakendus.dentalclinic.pet;

import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081", "http://iti0302dentalclinic.hopto.org/", "http://193.40.255.20/"})
@RestController
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping("/pet")
    public PetDTO addPet(@RequestBody PetDTO petDTO) {
        return petService.addPet(petDTO);
    }
    @GetMapping("/pet")
    public List<PetDTO> getAllPets() {
        return petService.findAll();
    }

}
