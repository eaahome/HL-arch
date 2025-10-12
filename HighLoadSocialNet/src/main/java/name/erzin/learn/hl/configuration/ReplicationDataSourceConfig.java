package name.erzin.learn.hl.configuration;

import name.erzin.learn.hl.datasource.ReplicationRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Replication DataSources Configuration
 *
 * <code>@Primary</code> and <code>@DependsOn</code> are the key requirements for Spring Boot.
 */
@Configuration
public class ReplicationDataSourceConfig {
    @Autowired
    private DatasourceProperties datasourceProperties;

    /**
     * Main DataSource
     * <p>
     * Application must use this dataSource.
     */
    @Primary
    @Bean
    // @DependsOn required!! thanks to Michel Decima
    @DependsOn({"writeDataSource", "readDataSource", "routingDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    /**
     * AbstractRoutingDataSource and it's sub classes must be initialized as Spring Bean for calling
     * {@link AbstractRoutingDataSource#afterPropertiesSet()}.
     */
    @Bean
    public DataSource routingDataSource() {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeDataSource());
        dataSourceMap.put("read", readDataSource());
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());

        return routingDataSource;
    }

    @Bean
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(datasourceProperties.getWriteDatasource().getUrl())
                .username(datasourceProperties.getWriteDatasource().getUsername())
                .password(datasourceProperties.getWriteDatasource().getPassword())
                .build();
    }

    @Bean
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(datasourceProperties.getReadDatasource().getUrl())
                .username(datasourceProperties.getReadDatasource().getUsername())
                .password(datasourceProperties.getReadDatasource().getPassword())
                .build();
    }
}
