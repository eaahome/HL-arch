package name.erzin.learn.hl.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "highload")
public class DatasourceProperties {
    @Getter
    @Setter
    public static class Datasource {
        private String url;
        private String username;
        private String password;
    }

    private Datasource readDatasource;
    private Datasource writeDatasource;
}
