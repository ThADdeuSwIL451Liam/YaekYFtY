// 代码生成时间: 2025-09-16 14:39:45
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.stereotype.Component;
import java.util.Set;
import static java.util.stream.Collectors.joining;

@Component
public class FormValidatorComponent {

    private final Validator validator;

    public FormValidatorComponent() {
        // 创建ValidatorFactory并从中获取Validator实例
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public <T> boolean validate(T object) {
        // 验证对象，返回是否有验证错误
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            // 如果有验证错误，将错误信息以字符串形式返回
            String errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(joining("<<BR>>"));
            // 这里可以根据需要处理错误消息，例如抛出异常或记录日志
            // throw new IllegalArgumentException(errorMessages);
            System.err.println(errorMessages);
            return false;
        }
        return true;
    }

    /*
     * 这个方法可以被用来在调用层处理验证结果。
     * 例如，在控制器中，你可以调用validate方法并根据返回的boolean值来决定是否继续处理请求。
     */
    public <T> boolean executeValidationAndProceed(T object, Runnable proceed) {
        if (validate(object)) {
            proceed.run();
            return true;
        }
        return false;
    }
}
