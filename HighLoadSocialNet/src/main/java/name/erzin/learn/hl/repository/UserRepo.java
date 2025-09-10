package name.erzin.learn.hl.repository;


import name.erzin.learn.hl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface UserRepo extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM user",
            nativeQuery = true)
    ArrayList<User> findAll();

    @Query(
            value = "SELECT * FROM user where id = :id",
            nativeQuery = true)
    User findById (String id);
}
