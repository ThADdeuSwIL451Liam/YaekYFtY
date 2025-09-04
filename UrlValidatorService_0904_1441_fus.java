// 代码生成时间: 2025-09-04 14:41:58
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Service class for validating the validity of a URL.
 * This service uses Spring's RestTemplate to send a HEAD request to the URL,
 * and then checks the response status code to determine if the URL is valid.
 */
@Service
public class UrlValidatorService {

    private static final Logger logger = LoggerFactory.getLogger(UrlValidatorService.class);
    private RestTemplate restTemplate;

    /**
     * Constructor. Initializes the RestTemplate bean.
     */
    public UrlValidatorService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Validates a URL by sending a HEAD request and checking the response status.
     * @param url The URL to be validated.
     * @return Boolean indicating whether the URL is valid or not.
     * @throws IllegalArgumentException if the URL is null or malformed.
     */
    public boolean validateUrl(String url) {
        try {
            URL parsedUrl = new URL(url);
            ResponseEntity<String> response = restTemplate.headForHeaders(url);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Error validating URL: " + url, e);
            throw new IllegalArgumentException("Invalid or malformed URL: " + url);
        }
    }

    /**
     * Post-construct method to log service initialization.
     */
    @PostConstruct
    private void init() {
        logger.info("UrlValidatorService initialized.");
    }

    /**
     * Pre-destroy method to log service destruction.
     */
    @PreDestroy
    private void destroy() {
        logger.info("UrlValidatorService destroyed.");
    }
}
