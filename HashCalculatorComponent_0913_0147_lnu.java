// 代码生成时间: 2025-09-13 01:47:10
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class HashCalculatorComponent {

    // 构造函数
    public HashCalculatorComponent() {
    }

    // 初始化方法，在组件被创建后执行
    @PostConstruct
    private void init() {
        // 这里可以添加组件初始化逻辑
    }

    // 计算字符串的哈希值
    public String calculateHash(String input) {
        try {
            // 获取MD5 MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(StandardCharsets.UTF_8));
            // 计算哈希值并转换为Base64编码字符串
            byte[] digest = md.digest();
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            // 处理哈希算法不可用的异常
            throw new RuntimeException("Hash algorithm not available", e);
        }
    }

    // 可以添加其他哈希算法实现，例如SHA-256
    public String calculateSHA256Hash(String input) {
        try {
            // 获取SHA-256 MessageDigest实例
            MessageDigest sha256digest = MessageDigest.getInstance("SHA-256");
            sha256digest.update(input.getBytes(StandardCharsets.UTF_8));
            // 计算哈希值并转换为Base64编码字符串
            byte[] sha256Digest = sha256digest.digest();
            return Base64.getEncoder().encodeToString(sha256Digest);
        } catch (NoSuchAlgorithmException e) {
            // 处理哈希算法不可用的异常
            throw new RuntimeException("SHA-256 hash algorithm not available", e);
        }
    }

    // 可以添加其他业务逻辑和方法
    // ...
}