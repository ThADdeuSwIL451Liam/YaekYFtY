// 代码生成时间: 2025-09-15 04:24:07
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Component
public class JsonDataConverter implements Converter<String, Object> {

    private final ObjectMapper objectMapper;

    public JsonDataConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object convert(String source) {
        // 检查源字符串是否为空或空白
        if (StringUtils.isEmpty(source)) {
            throw new IllegalArgumentException("Source JSON string is empty or blank");
        }
        try {
            // 尝试将JSON字符串转换为对象
            return objectMapper.readValue(source, Object.class);
        } catch (IOException e) {
            // 捕获转换异常并抛出运行时异常
            throw new RuntimeException("Failed to convert JSON string to object", e);
        }
    }

    // 将对象转换为JSON字符串
    public String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }
}
