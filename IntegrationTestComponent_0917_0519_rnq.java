// 代码生成时间: 2025-09-17 05:19:40
 * IntegrationTestComponent.java
 *
 * This class demonstrates a simple Spring Boot component for integration testing,
 * incorporating error handling and following Spring Boot best practices.
 */

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

// Mark this class as a Spring Boot test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Component
public class IntegrationTestComponent {

    // Autowire the TestRestTemplate for making HTTP requests to the server under test
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test the application's home page
     */
    @Test
    public void testHomePage() {
        // Make a GET request to the root endpoint
        ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

        // Assert that the status code is 200 OK
        assertThat(response.getStatusCode().is2xxSuccessful()).as("Response status code").isTrue();

        // Assert the body contains the expected string
        assertThat(response.getBody()).contains("Welcome to the application!");
    }

    /**
     * Simulate an error scenario and test error handling
     */
    @Test
    public void testErrorHandling() {
        // Make a GET request to a non-existent endpoint to trigger a 404 error
        ResponseEntity<String> response = restTemplate.getForEntity("/non-existent", String.class);

        // Assert that the status code is 404 NOT_FOUND
        assertThat(response.getStatusCode().is4xxClientError()).as("Response status code").isTrue();

        // Assert the error body contains the expected error message
        assertThat(response.getBody()).contains("Resource not found");
    }
}
