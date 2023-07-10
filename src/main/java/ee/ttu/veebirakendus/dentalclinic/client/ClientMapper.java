package ee.ttu.veebirakendus.dentalclinic.client;

import ee.ttu.veebirakendus.dentalclinic.employee.EmployeeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = EmployeeRepository.class)
public interface ClientMapper {
    @Mapping(source = "doctor.firstName", target = "doctorName")
    ClientDTO toDto(Client client);

    @Mapping(source = "doctorName", target = "doctor")
    Client fromDto(ClientDTO clientDTO);

    List<ClientDTO> toDtoList(List<Client> client);
}
