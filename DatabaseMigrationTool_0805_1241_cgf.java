// 代码生成时间: 2025-08-05 12:41:21
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import liquibase.Liquibase;
import liquibase.configuration.GlobalConfiguration;
import liquibase.configuration.ConfigurationValueProvider;
import liquibase.configuration.core.DefaultValues;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.Contexts;
import liquibase.LabelExpression;
# 改进用户体验
import liquibase.changelog.ChangeSet;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.executor.jvm.JdbcExecutor;
import liquibase.logging.Logger;
import liquibase.resource.ResourceAccessor;
import liquibase.servicelocator.ServiceLocator;
import liquibase.util.StreamUtil;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
# TODO: 优化性能
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DatabaseMigrationTool implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseMigrationTool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        // Set up Liquibase properties
# FIXME: 处理边界情况
        Properties props = new Properties();
        props.setProperty(GlobalConfiguration.DATABASE_CHANGE_LOG_LOCK_WAIT_TIME.getKey(), "10");
        props.setProperty(GlobalConfiguration.DATABASE_CHANGE_LOG_PARAMETER_SKIP_LOCK_CHECK.getKey(), "true");
        props.setProperty(GlobalConfiguration.DATABASE_CHANGE_LOG_PARAMS.getKey(), "example.parameter=example.value");
        
        try {
            // Initialize Liquibase
            Liquibase liquibase = new Liquibase(
                "classpath:db/changelog/changelog-master.xml", // Changelog file path
# NOTE: 重要实现细节
                new ClassLoaderResourceAccessor(), // Resource accessor
                props // Properties
            );

            // Perform migration
            liquibase.update(new Contexts(), new LabelExpression());
            System.out.println("Database migration completed successfully.");
        } catch (LiquibaseException e) {
            // Handle Liquibase exceptions
            System.err.println("Error performing database migration: " + e.getMessage());
            throw e;
        }
    }
}
# 改进用户体验
