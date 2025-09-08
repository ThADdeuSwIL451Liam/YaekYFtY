// 代码生成时间: 2025-09-09 04:05:09
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.HashMap;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private RestTemplate restTemplate;
    
    @PostConstruct
    public void init() {
        // 初始化代码，例如：检查配置、初始化连接等
    }
    
    public ResponseEntity<String> sendNotification(String message, String targetUrl) {
        try {
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("message", message);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestMap);
            return restTemplate.postForEntity(targetUrl, request, String.class);
        } catch (HttpClientErrorException e) {
            logger.error("Failed to send notification: " + e.getMessage());
            return new ResponseEntity<>("Error sending notification", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("General error: " + e.getMessage());
            return new ResponseEntity<>("Error sending notification", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/notify")
    public ResponseEntity<String> notify(@RequestParam String message, @RequestParam String url) {
        return sendNotification(message, url);
    }
    
    // 异常处理方法
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException e) {
        logger.error("HTTP error occurred: " + e.getMessage());
        return new ResponseEntity<>("Error from the server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // 可以添加更多异常处理方法
}