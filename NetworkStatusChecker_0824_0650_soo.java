// 代码生成时间: 2025-08-24 06:50:11
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class NetworkStatusChecker {

    @Value("{url.to.check}")
    private String urlToCheck; // Replace with the actual URL to check

    private final RestTemplate restTemplate;

    public NetworkStatusChecker() {
        // Use a connection timeout of 5000 milliseconds and a read timeout of 5000 milliseconds
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * Checks the network connection to the specified URL.
     * @return true if the network connection is successful, false otherwise.
     */
    public boolean checkNetworkConnection() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(urlToCheck, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return true;
            }
        } catch (ResourceAccessException e) {
            // Handle resource access exception
            System.out.println("ResourceAccessException: " + e.getMessage());
        } catch (RestClientException e) {
            // Handle other rest client exceptions
            System.out.println("RestClientException: " + e.getMessage());
        }
        return false;
    }

    /**
     * Checks if the local machine's network interface is up.
     * @return true if the network interface is up, false otherwise.
     */
    public boolean checkLocalNetworkInterface() {
        try {
            InetAddress.getLocalHost();
            return true; // If no exception is thrown, the network interface is up
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException: " + e.getMessage());
            return false;
        }
    }
}
