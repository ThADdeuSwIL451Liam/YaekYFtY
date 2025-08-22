// 代码生成时间: 2025-08-23 01:33:25
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
# 扩展功能模块
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Component
# 增强安全性
public class TestReportGenerator {

    private final ResourceLoader resourceLoader;

    @Value("classpath:test-reports/")
    private String testReportBasePath;
# NOTE: 重要实现细节

    public TestReportGenerator(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateTestReport(@RequestParam String reportName) {
        try {
            String reportPath = testReportBasePath + reportName;
            String reportContent = new String(Files.readAllBytes(Paths.get(reportPath)));
            return ResponseEntity.ok(reportContent);
# NOTE: 重要实现细节
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate test report.");
        }
    }

    // Helper method to load test report file
    private byte[] loadTestReportFile(String reportPath) throws IOException {
        return resourceLoader.getResource(reportPath).getInputStream().readAllBytes();
    }
}
