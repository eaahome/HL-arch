package name.erzin.learn.hl.repository;

import name.erzin.learn.hl.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query(
            value = "SELECT * FROM account",
            nativeQuery = true)
    ArrayList<Account> findAll();

    @Query(
            value = "SELECT login, password FROM account where login = :login",
            nativeQuery = true)
    ArrayList<Account> findByLogin (String login);
}
