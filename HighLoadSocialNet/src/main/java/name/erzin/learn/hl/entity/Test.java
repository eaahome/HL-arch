package name.erzin.learn.hl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Test {
    @Id
    Integer id;
    String firstName;
    String lastName;
}
