package name.erzin.learn.hl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Friend {
    @Id
    String userId;
    private String friendId;
}
