// 代码生成时间: 2025-08-10 21:47:38
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Test data generator component for creating fake data.
 */
@Component
@Profile("test") // Activate this component only in test profile
public class TestDataGenerator {

    private static final String[] NAMES = {"Alice", "Bob", "Charlie", "David", "Eve"};
    private static final Random RANDOM = new Random();

    private final Config config;

    @Autowired
    public TestDataGenerator(Config config) {
        this.config = config;
    }

    /**
     * Generate a list of test data.
     * 
     * @return List of generated test data.
     */
    public List<TestData> generateTestData(int count) {
        List<TestData> testDataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            TestData testData = new TestData();
            testData.setName(NAMES[RANDOM.nextInt(NAMES.length)]);
            testData.setAge(RANDOM.nextInt(100));
            testData.setEmail(generateEmail(testData.getName()));
            testDataList.add(testData);
        }
        return testDataList;
    }

    /**
     * Generate a random email based on the given name.
     * 
     * @param name The name to use for generating the email.
     * @return The generated email address.
     */
    private String generateEmail(String name) {
        // Simple email generation logic
        String emailNamePart = StringUtils.capitalize(name.toLowerCase().replace(" ", ""));
        return emailNamePart + "@example.com";
    }

    /**
     * Test data class.
     */
    public static class TestData {
        private String name;
        private int age;
        private String email;

        // Getters and setters
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
    }
}