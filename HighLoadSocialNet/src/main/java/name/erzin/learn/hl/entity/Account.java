package name.erzin.learn.hl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Account {
    @Id
    String login;
    String password;
}
