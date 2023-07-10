package ee.ttu.veebirakendus.dentalclinic.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081", "http://iti0302dentalclinic.hopto.org/", "http://193.40.255.20/"})
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/account")
    public List<AccountDTO> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping(value = "/account", params = "id")
    public AccountDTO getAccountById(@RequestParam int id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/public/account")
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.addAccount(accountDTO);
    }

    @DeleteMapping("/account")
    public ResponseEntity<String> delete(@RequestParam String email) {
        return accountService.deleteAccountByEmail(email);
    }

    @PostMapping("/public/account/token")
    public ResponseEntity<String> login(@RequestBody AccountDTO accountDTO) {
        return accountService.login(accountDTO.getEmail(), accountDTO.getPlainPassword());
    }
}
