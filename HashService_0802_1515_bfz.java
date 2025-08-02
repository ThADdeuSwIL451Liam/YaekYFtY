// 代码生成时间: 2025-08-02 15:15:47
 * Service component for calculating hash values of strings.
 * It follows Spring Boot best practices, includes error handling,
 * and is well-documented.
 */

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashService {

    @Autowired
    private HashConfig hashConfig;

    public String calculateHash(String input) throws NoSuchAlgorithmException {
        // Check if the input is null or empty
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        // Get the algorithm from configuration
        String algorithm = hashConfig.getAlgorithm();

        // Calculate the hash using the specified algorithm
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] digest = md.digest(input.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
