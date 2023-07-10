package ee.ttu.veebirakendus.dentalclinic.account;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsAccountByEmail(String email);

    Optional<Account> findAccountByEmail(String email);
}
