// 代码生成时间: 2025-08-06 13:16:48
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public HandlerExceptionResolver responseEntityExceptionHandler() {
        return new ResponseEntityExceptionHandler();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    // 使用JdbcTemplate防止SQL注入
    @RequestMapping("/getData")
    public String getData() {
        JdbcTemplate jdbcTemplate = jdbcTemplate(/* DataSource */);
        String sql = "SELECT * FROM users WHERE name = ?";
        jdbcTemplate.queryForObject(sql, new Object[] { "unsafeInput" }, String.class);
        // 为了演示，这里使用了一个不安全的输入
        // 在实际应用中，应该通过验证或者使用参数化查询来防止SQL注入
        return "data";
    }

    // 使用NamedParameterJdbcTemplate防止SQL注入
    @RequestMapping("/getUserData")
    public String getUserData() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = namedParameterJdbcTemplate(/* DataSource */);
        String sql = "SELECT * FROM users WHERE name = :name";
        namedParameterJdbcTemplate.queryForObject(sql, new org.springframework.jdbc.core.namedparam.MapSqlParameterSource("name", "unsafeInput"), String.class);
        // 同样，这里使用了不安全的输入
        // 在实际应用中，应该通过验证或者使用参数化查询来防止SQL注入
        return "userData";
    }

    // 错误处理
    @RequestMapping("/error")
    public String handleError() {
        // 模拟错误
        throw new RuntimeException("An error occurred");
    }
}
