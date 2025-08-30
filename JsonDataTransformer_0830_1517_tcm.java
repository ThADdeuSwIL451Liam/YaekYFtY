// 代码生成时间: 2025-08-30 15:17:36
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

/**
 * JSON数据格式转换器，用于将JSON字符串转换为Map对象
 */
@Component
@Validated
public class JsonDataTransformer implements ConverterFactory<String, Map<String, Object>> {

    private final ObjectMapper objectMapper;

    public JsonDataTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Converter<String, Map<String, Object>> getConverter(Class<?> targetType) {
        return source -> {
            try {
                // 尝试将JSON字符串转换为Map对象
                return objectMapper.readValue(source, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
            } catch (JsonProcessingException e) {
                // 处理转换错误，抛出IllegalArgumentException
                throw new IllegalArgumentException("Invalid JSON format: " + source, e);
            } catch (IOException e) {
                // 处理IO异常，抛出RuntimeException
                throw new RuntimeException("Error parsing JSON: " + source, e);
            }
        };
    }

    /**
     * 验证传入的JSON字符串是否有效
     *
     * @param json JSON字符串
     * @return 验证结果
     */
    public boolean isValidJson(@NotNull String json) {
        try {
            // 尝试解析JSON字符串，如果解析成功则返回true
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            // 解析失败则返回false
            return false;
        }
    }
}
