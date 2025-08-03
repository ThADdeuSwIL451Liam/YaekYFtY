// 代码生成时间: 2025-08-04 02:35:34
package com.example.filebackupsync;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileSystemUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Service class for file backup and synchronization.
 */
@Service
public class FileBackupAndSyncService {

    private final ResourceLoader resourceLoader;
    private final Path backupDirectory;

    public FileBackupAndSyncService(ResourceLoader resourceLoader,
                                  @Value("${backup.directory}") String backupDirectoryPath) {
        this.resourceLoader = resourceLoader;
        this.backupDirectory = Paths.get(backupDirectoryPath);
    }

    /**
     * Synchronizes a file with the backup directory.
     *
     * @param filename The name of the file to be synchronized.
     */
    @Retryable(value = IOException.class, maxAttempts = 3)
    @Async
    public void synchronizeFile(String filename) {
        try {
            Path sourceFile = Paths.get(filename);
            Path backupFile = this.backupDirectory.resolve(sourceFile.getFileName());

            if (Files.exists(backupFile)) {
                // If backup file exists, compare and overwrite if necessary
                if (!Files.isSameFile(sourceFile, backupFile)) {
                    Files.copy(sourceFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                // If backup file does not exist, create it
                Files.createDirectories(this.backupDirectory);
                Files.copy(sourceFile, backupFile);
            }
        } catch (IOException e) {
            // Log the error and rethrow the exception
            throw new RuntimeException("Error synchronizing file: " + filename, e);
        }
    }

    /**
     * Deletes old backups to prevent disk space overflow.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void cleanupOldBackups() {
        try {
            if (Files.exists(this.backupDirectory)) {
                Files.walk(this.backupDirectory)
                        .sorted((p1, p2) -> Long.compare(p2.toFile().lastModified(), p1.toFile().lastModified()))
                        .filter(p -> Files.isRegularFile(p))
                        .map(Path::toFile)
                        .forEach(file -> {
                            try {
                                if (file.lastModified() < System.currentTimeMillis() - 604800000L) {
                                    // Delete files older than 7 days
                                    file.delete();
                                }
                            } catch (IOException e) {
                                // Log the error
                            }
                        });
            }
        } catch (IOException e) {
            // Log the error
            throw new RuntimeException("Error cleaning up old backups", e);
        }
    }

    /**
     * Initializes the backup directory.
     */
    public void initBackupDirectory() {
        try {
            if (!Files.exists(this.backupDirectory)) {
                Files.createDirectories(this.backupDirectory);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing backup directory", e);
        }
    }
}
