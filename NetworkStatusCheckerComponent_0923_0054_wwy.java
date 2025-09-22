// 代码生成时间: 2025-09-23 00:54:17
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NetworkStatusCheckerComponent {

    private static final Logger logger = LoggerFactory.getLogger(NetworkStatusCheckerComponent.class);

    @Value("${network.check.url}")
    private String url;
    @Value("${network.check.timeout:5000}")
    private int timeout;

    public boolean isReachable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(url, 80), timeout);
            return socket.isConnected();
        } catch (IOException e) {
            logger.error("Error checking network connection: ", e);
            return false;
        }
    }

    // Sets the URL to check for network connectivity
    public void setUrl(String url) {
        this.url = url;
    }

    // Returns the timeout for network connectivity check in milliseconds
    public int getTimeout() {
        return timeout;
    }

    // Sets the timeout for network connectivity check in milliseconds
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
