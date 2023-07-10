package ee.ttu.veebirakendus.dentalclinic.account;

import ee.ttu.veebirakendus.dentalclinic.department.DepartmentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DepartmentRepository.class)
public interface AccountMapper {
    AccountDTO toDto(Account account);

    @Mapping(source = "plainPassword", target = "hashPassword", qualifiedByName = "plainToHash")
    Account fromDto(AccountDTO accountDTO);

    List<AccountDTO> toDtoList(List<Account> accounts);

    @Named("plainToHash")
    static String plainToHashPassword(String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }
}
