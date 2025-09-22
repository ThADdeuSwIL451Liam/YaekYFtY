// 代码生成时间: 2025-09-22 11:08:03
import org.springframework.stereotype.Component;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class MemoryAnalysisComponent implements CommandLineRunner {

    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @Override
    public void run(String... args) throws Exception {
        // Analyze memory usage when the application starts
        analyzeMemoryUsage();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void handleContextRefresh() {
        // Analyze memory usage when the application is ready
        analyzeMemoryUsage();
    }

    private void analyzeMemoryUsage() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            long usedMemory = heapMemoryUsage.getUsed();
            long maxMemory = heapMemoryUsage.getMax();
            long committedMemory = heapMemoryUsage.getCommitted();
            long usedPermGen = nonHeapMemoryUsage.getUsed();
            long maxPermGen = nonHeapMemoryUsage.getMax();

            // Log or display memory usage statistics
            System.out.println("Used Memory: " + usedMemory + " bytes");
            System.out.println("Max Memory: " + maxMemory + " bytes");
            System.out.println("Committed Memory: " + committedMemory + " bytes");
            System.out.println("Used PermGen (Non-Heap): " + usedPermGen + " bytes");
            System.out.println("Max PermGen (Non-Heap): " + maxPermGen + " bytes");

        } catch (Exception e) {
            // Handle exception by logging or other error handling
            System.err.println("Error while analyzing memory usage: " + e.getMessage());
        }
    }
}
