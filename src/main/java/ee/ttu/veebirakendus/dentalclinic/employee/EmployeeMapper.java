package ee.ttu.veebirakendus.dentalclinic.employee;


import ee.ttu.veebirakendus.dentalclinic.department.DepartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DepartmentRepository.class)

public interface EmployeeMapper {
    @Mapping(source = "department.name", target = "departmentName")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "departmentName", target = "department")
    Employee fromDto(EmployeeDTO employeeDTO);

    List<EmployeeDTO> toDtoList(List<Employee> employee);
}
