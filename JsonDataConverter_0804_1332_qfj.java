// 代码生成时间: 2025-08-04 13:32:01
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;

/**
 * JSON数据格式转换器，用于将JSON字符串转换为Map对象。
 * 使用Spring Boot提供的注解和最佳实践来实现。
 */
@Component
public class JsonDataConverter implements ConverterFactory<String, Map<String, Object>> {

    private final ObjectMapper objectMapper;

    public JsonDataConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Converter<String, Map<String, Object>> getConverter(Class<?> targetType) {
        return source -> {
            // 检查源字符串是否为空
            if (!StringUtils.hasText(source)) {
                throw new IllegalArgumentException("Source JSON string cannot be null or empty");
            }
            try {
                // 将JSON字符串转换为Map对象
                return objectMapper.readValue(source, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                // 处理转换错误
                throw new RuntimeException("Failed to convert JSON to Map: " + source, e);
            }
        };
    }
}
