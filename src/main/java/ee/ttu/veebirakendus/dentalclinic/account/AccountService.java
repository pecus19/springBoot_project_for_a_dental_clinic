package ee.ttu.veebirakendus.dentalclinic.account;

import ee.ttu.veebirakendus.dentalclinic.configuration.JwtTokenProvider;
import ee.ttu.veebirakendus.dentalclinic.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    private Account getById(int id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id %d was not found.".formatted(id)));
    }

    private Account getByEmail(String email) {
        return accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account with email %s was not found.".formatted(email)));
    }

    public AccountDTO getAccountById(int id) {
        return accountMapper.toDto(getById(id));
    }

    public AccountDTO addAccount(AccountDTO accountDTO) throws IllegalArgumentException {
        if (accountRepository.existsAccountByEmail(accountDTO.getEmail())) {
            throw new IllegalArgumentException("Account with email %s already exists".formatted(accountDTO.getEmail()));
        } else {
            if (isValidEmail(accountDTO.getEmail())) {
                Account account = accountMapper.fromDto(accountDTO);
                return accountMapper.toDto(accountRepository.save(account));
            } else {
                throw new IllegalArgumentException("Invalid email address.");
            }
        }
    }

    public static boolean isValidEmail(String emailAddress) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }

    @Transactional
    public ResponseEntity<String> deleteAccountByEmail(String email) {
        Account account = getByEmail(email);
        accountRepository.delete(account);
        return new ResponseEntity<>("Account was successfully deleted.", HttpStatus.OK);
    }

    public List<AccountDTO> findAll() {
        return accountMapper.toDtoList(accountRepository.findAll());
    }

    public ResponseEntity<String> login(String email, String password) {
        Account account = getByEmail(email);
        if (new BCryptPasswordEncoder().matches(password, account.getHashPassword())) {
            return new ResponseEntity<>(JwtTokenProvider.generateToken(email), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect password.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
