// 代码生成时间: 2025-09-24 11:52:23
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个Spring Boot组件，遵循最佳实践，包括错误处理。
 */
@RestController
@RequestMapping("/api")
@Component
public class SpringBootApplicationComponent {

    /**
     * 一个简单的GET请求处理方法，返回一个消息。
     * 
     * @param message 参数，用于返回的消息内容
     * @return ResponseEntity对象，包含响应体和状态码
     */
    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(value = "message", defaultValue = "World") String message) {
        return ResponseEntity.ok("Hello, " + message);
    }

    /**
     * 全局异常处理器
     */
    @ControllerAdvice
    public class GlobalExceptionHandler {

        /**
         * 处理方法参数验证异常
         * 
         * @param ex 异常对象
         * @return ResponseEntity对象，包含错误信息和状态码
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }
}
