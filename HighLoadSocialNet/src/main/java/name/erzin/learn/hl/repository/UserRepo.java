package name.erzin.learn.hl.repository;


import jakarta.transaction.Transactional;
import name.erzin.learn.hl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM user", nativeQuery = true)
    ArrayList<User> findAll();

    @Query(value = "SELECT * FROM user where id = :id", nativeQuery = true)
    Optional<User> findById (String id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user (id, first_name, second_name, birthdate, sex, biography, city)"
            + " VALUES (:id, :firstName, :secondName, :birthdate, :sex, :biography, :city)",
            nativeQuery = true)
    void insertUser (String id, String firstName, String secondName, String birthdate, String sex, String biography,
                     String city);
}
