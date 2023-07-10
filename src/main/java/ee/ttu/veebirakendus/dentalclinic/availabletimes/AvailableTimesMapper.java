package ee.ttu.veebirakendus.dentalclinic.availabletimes;

import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = EmployeeRepository.class)
public interface AvailableTimesMapper {
    @Mapping(source = "doctor.firstName", target = "doctorName")
    AvailableTimesDTO toDto(AvailableTimes availableTimes);

    @Mapping(source = "doctorName", target = "doctor")
    AvailableTimes fromDto(AvailableTimesDTO availableTimesDTO);

    List<AvailableTimesDTO> toDtoList(List<AvailableTimes> availableTimes);
}
