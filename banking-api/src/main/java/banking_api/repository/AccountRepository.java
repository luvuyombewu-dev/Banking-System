package banking_api.repository;

import banking_api.model.Account;
import banking_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUser(User user);

    Optional<Account> findByAccountNumber(String accountNumber);

}