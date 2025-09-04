package name.erzin.learn.hl.repository;

import name.erzin.learn.hl.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface TestRepo extends JpaRepository<Test, Long> {
    @Query(
            value = "SELECT * FROM test",
            nativeQuery = true)
    ArrayList<Test> findAll();
}
