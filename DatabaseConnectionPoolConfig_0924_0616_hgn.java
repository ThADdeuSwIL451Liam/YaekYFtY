// 代码生成时间: 2025-09-24 06:16:54
package com.example.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.EnableRetry;
import javax.sql.DataSource;

@Configuration
@EnableRetry
public class DatabaseConnectionPoolConfig {

    @Value("\${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("\${spring.datasource.url}")
    private String url;

    @Value("\${spring.datasource.username}")
    private String username;

    @Value("\${spring.datasource.password}")
    private String password;

    private static final int MAXACTIVE = 50;
    // Maximum number of active connections that can be allocated from this pool at the same time.

    private static final int MAXIDLE = 20;
    // Maximum number of idle connections that can remain in the pool.

    private static final int MINIDLE = 5;
    // Minimum number of idle connections that can remain in the pool.

    private static final long MAXWAIT = 10000;
    // Maximum time to wait for a connection to be returned to the pool.

    @Bean
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Set connection pool configurations
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(MAXACTIVE);
        dataSource.setMaxIdle(MAXIDLE);
        dataSource.setMinIdle(MINIDLE);
        dataSource.setMaxWaitMillis(MAXWAIT);

        // Handle database connection errors
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");

        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/migration/init-db.sql"));
        return populator;
    }

}