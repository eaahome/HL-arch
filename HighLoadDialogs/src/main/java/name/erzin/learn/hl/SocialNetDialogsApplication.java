package name.erzin.learn.hl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan ({"name.erzin.learn.hl.api", "name.erzin.learn.hl"})
@EnableJpaRepositories
@SpringBootApplication
public class SocialNetDialogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNetDialogsApplication.class, args);
    }
}

