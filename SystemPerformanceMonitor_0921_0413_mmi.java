// 代码生成时间: 2025-09-21 04:13:53
import org.springframework.stereotype.Component;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.AttributeList;
import javax.management.Attribute;
import javax.management.MalformedObjectNameException;
import java.lang.management.ManagementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@Component
@RestController
public class SystemPerformanceMonitor {
    
    private static final Logger logger = LoggerFactory.getLogger(SystemPerformanceMonitor.class);

    // 获取系统性能信息
    @GetMapping("/performance")
    public ResponseEntity<String> getPerformanceMetrics() {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("java.lang:type=OperatingSystem");
            AttributeList attributes = mBeanServer.getMBeanInfo(objectName).getAttributes();
            StringBuilder sb = new StringBuilder();
            for (Object obj : attributes.asList()) {
                Attribute attribute = (Attribute) obj;
                sb.append(attribute.getName()).append(": ").append(attribute.getValue()).append("
");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching system performance metrics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
