// 代码生成时间: 2025-09-17 22:12:27
import org.springframework.stereotype.Component;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Component
public class JsonDataConverter extends MappingJackson2HttpMessageConverter {

    private final ObjectMapper objectMapper;

    public JsonDataConverter() {
        super();
        this.objectMapper = super.getObjectMapper();
        this.setSupportedMediaTypes(List.of(org.springframework.http.MediaType.APPLICATION_JSON));
    }

    /**
     * Convert JSON string to Java object.
     * @param json JSON string
     * @param valueType the class of the object to convert to
     * @param <T> the type of the object to convert to
     * @return the converted Java object
     * @throws IOException if JSON is not well-formed
     */
    public <T> T convertToJavaObject(String json, Class<T> valueType) throws IOException {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON format", e);
        }
    }

    /**
     * Convert Java object to JSON string.
     * @param value the object to convert
     * @param <T> the type of the object to convert
     * @return the JSON representation of the object
     * @throws HttpMessageNotWritableException if the object cannot be written to JSON
     */
    public <T> String convertToJson(T value) throws HttpMessageNotWritableException {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + e.getMessage());
        }
    }

    /**
     * Error handling method for JSON conversion errors.
     * @param ex the exception that occurred during conversion
     * @return a user-friendly error message
     */
    public String handleErrors(Exception ex) {
        return "Error during JSON conversion: " + ex.getMessage();
    }
}
