// 代码生成时间: 2025-08-19 07:52:21
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;

@Component
public class ImageResizerComponent {

    private final int targetWidth = 800; // 目标图片宽度
    private final int targetHeight = 600; // 目标图片高度

    public ImageResizerComponent() {
    }

    @PostConstruct
    public void init() {
        // 初始化方法，可以进行一些必要的设置
    }

    public void resizeImages(String directoryPath) throws IOException {
        Path directory = Paths.get(directoryPath);
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Provided path is not a valid directory.");
        }

        Files.walk(directory).forEach(filePath -> {
            File file = filePath.toFile();
            if (file.isFile() && isImage(file)) {
                try {
                    resizeImage(file);
                } catch (IOException e) {
                    // 错误处理，记录日志或采取其他措施
                    System.err.println("Error resizing image: " + file.getName());
                }
            }
        });
    }

    private boolean isImage(File file) {
        // 检查文件是否是图片
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
    }

    private void resizeImage(File originalImage) throws IOException {
        BufferedImage original = ImageIO.read(originalImage);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, original.getType());
        resizedImage.getGraphics().drawImage(original.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);

        // 保存调整后的图片
        ImageIO.write(resizedImage, getExtension(originalImage), originalImage);
    }

    private String getExtension(File file) {
        // 获取图片文件的扩展名
        String fileName = file.getName();
        if (fileName.lastIndexOf('.') != -1) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return "";
    }
}
