package ee.ttu.veebirakendus.dentalclinic.pet;

import ee.ttu.veebirakendus.dentalclinic.account.AccountDTO;
import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeDTO;
import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeMapper;
import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public PetDTO addPet(PetDTO petDTO) {
        return petMapper.toDto(petRepository.save(petMapper.fromDto(petDTO)));
    }
    public List<PetDTO> findAll() {
        return petMapper.toDtoList(petRepository.findAll());
    }
}
