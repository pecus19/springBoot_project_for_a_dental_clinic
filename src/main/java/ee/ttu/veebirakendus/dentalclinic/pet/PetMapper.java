package ee.ttu.veebirakendus.dentalclinic.pet;

import ee.ttu.veebirakendus.dentalclinic.department.DepartmentRepository;
import ee.ttu.veebirakendus.dentalclinic.employee.Employee;
import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DepartmentRepository.class)
public interface PetMapper {
    PetDTO toDto(Pet pet);

    Pet fromDto(PetDTO petDTO);

    List<PetDTO> toDtoList(List<Pet> pet);
}
