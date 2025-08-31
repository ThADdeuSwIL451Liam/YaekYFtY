// 代码生成时间: 2025-08-31 23:41:36
package com.example.demo.component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class ImageResizerComponent {

    private static final String TEMP_DIR = "./uploads";

    public List<BufferedImage> resizeImages(List<MultipartFile> multipartFileList, int targetWidth, int targetHeight) {
        return multipartFileList.stream()
                .map(this::resizeImage)
                .toList();
    }

    private BufferedImage resizeImage(MultipartFile multipartFile) {
        try {
            // Create a temp file to store the uploaded file
            Path tempFilePath = Paths.get(TEMP_DIR, multipartFile.getOriginalFilename());
            Files.createDirectories(tempFilePath.getParent());
            Files.copy(multipartFile.getInputStream(), tempFilePath);

            // Read the image file
            File file = tempFilePath.toFile();
            BufferedImage originalImage = ImageIO.read(file);

            // Resize the image
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
            resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);

            // Clean up the temp file
            Files.deleteIfExists(tempFilePath);

            return resizedImage;

        } catch (IOException e) {
            throw new RuntimeException("Failed to resize image", e);
        }
    }

    // Method to save the resized images to a specified directory
    public void saveResizedImages(List<BufferedImage> resizedImages, String directoryPath) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
            for (int i = 0; i < resizedImages.size(); i++) {
                BufferedImage resizedImage = resizedImages.get(i);
                String filename = "resized_" + (i + 1) + "." + determineFileExtension(resizedImage);
                Path outputPath = Paths.get(directoryPath, filename);
                ImageIO.write(resizedImage, filename.substring(filename.lastIndexOf(".") + 1), outputPath.toFile());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save resized images", e);
        }
    }

    private String determineFileExtension(BufferedImage image) {
        String formatName = image.getProperty("comment") != null ? image.getProperty("comment").toString() : "png";
        return formatName.equalsIgnoreCase("jpeg") ? "jpg" : formatName;
    }
}
