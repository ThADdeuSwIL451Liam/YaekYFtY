// 代码生成时间: 2025-08-14 18:28:19
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FormValidator {

    @PostMapping("/validate")
    public Map<String, String> validateFormData(@Validated @RequestBody FormData formData, BindingResult bindingResult) {
        // Create a map to store our response
        Map<String, String> response = new HashMap<>();

        // Check for binding errors
        if (bindingResult.hasErrors()) {
            // If there are errors, construct a response message
            String errorMessage = "Validation failed: " + bindingResult.getFieldError().getDefaultMessage();
            response.put("error", errorMessage);
        } else {
            // If no errors, return a success message
            response.put("message", "Validation successful!");
        }

        // Return the response map
        return response;
    }

    // Inner class to represent form data
    public static class FormData {
        // Example fields with validation annotations
        // You can add additional fields and validation annotations as needed
        private String username;
        private String email;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
