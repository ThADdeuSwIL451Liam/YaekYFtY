// 代码生成时间: 2025-09-21 22:34:11
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * FileDecompressor component responsible for decompressing files in a Spring Boot application.
 */
@Component
public class FileDecompressor {

    private static final String TEMP_DIR = "./tmp/"; // Temporary directory for decompression

    /**
     * Decompresses a ZIP file into the specified directory.
     * 
     * @param file The ZIP file to decompress.
     * @param destinationDirectory The directory where the file will be decompressed.
     * @return The path to the decompressed files.
     * @throws IOException If an I/O error occurs during decompression.
     */
    public String decompressFile(MultipartFile file, String destinationDirectory) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The file is empty.");
        }

        // Create the destination directory if it does not exist
        Files.createDirectories(Paths.get(destinationDirectory));

        // Create a ZipInputStream to read the ZIP file
        try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream())) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                // Create a file path for the current ZipEntry
                File destinationFile = new File(destinationDirectory + File.separator + zipEntry.getName());

                // Create directories and files as required
                if (zipEntry.isDirectory()) {
                    if (!destinationFile.exists()) {
                        Files.createDirectory(destinationFile.toPath());
                    }
                } else {
                    File parent = destinationFile.getParentFile();
                    if (!parent.exists()) {
                        Files.createDirectories(parent.toPath());
                    }
                    try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) >= 0) {
                            fileOutputStream.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
        return destinationDirectory;
    }
}
