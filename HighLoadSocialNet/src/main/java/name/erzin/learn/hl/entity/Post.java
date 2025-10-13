package name.erzin.learn.hl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Post {
    @Id
    String id;
    private String text;
    private String authorUserId;
    private Instant createdAt;
}
