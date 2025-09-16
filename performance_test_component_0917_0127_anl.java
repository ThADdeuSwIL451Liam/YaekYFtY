// 代码生成时间: 2025-09-17 01:27:15
package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.util.Random;
import java.time.Duration;
import java.time.Instant;

@Component
public class PerformanceTestComponent {

    // Inject the base URL for the performance test
    @Value("${performance.test.url}")
    private String performanceTestUrl;

    // A RestTemplate instance for making HTTP requests
    private final RestTemplate restTemplate;

    // Constructor with RestTemplate as an argument for Dependency Injection
    public PerformanceTestComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Perform a performance test by sending multiple requests to the specified URL.
     * @param numberOfRequests Number of requests to send.
     * @return A string representing the result of the performance test.
     */
    public String performPerformanceTest(int numberOfRequests) {
        // Keep track of the total time taken to send all requests
        long totalTime = 0;
        for (int i = 0; i < numberOfRequests; i++) {
            try {
                long startTime = Instant.now().toEpochMilli();

                // Send a GET request and measure the time taken
                ResponseEntity<String> response = restTemplate.getForEntity(performanceTestUrl, String.class);

                // Check if the response status is OK
                if (response.getStatusCode() == HttpStatus.OK) {
                    long endTime = Instant.now().toEpochMilli();
                    totalTime += endTime - startTime;
                } else {
                    // Log an error if the response status is not OK
                    System.err.println("Error: Received status code " + response.getStatusCode().value());
                }
            } catch (Exception e) {
                // Handle exceptions and log them
                System.err.println("Error during performance test: " + e.getMessage());
            }
        }
        // Calculate and return the average response time
        return "Average response time: " + (totalTime / numberOfRequests) + " ms";
    }

    /**
     * A method that simulates a random delay to mimic variable network conditions.
     * @return A random delay in milliseconds.
     */
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public String simulateNetworkDelay() throws InterruptedException {
        Random random = new Random();
        int delay = random.nextInt(5000); // Random delay between 0 and 5000 ms
        Thread.sleep(delay);
        return "Simulated delay of " + delay + " ms";
    }
}
