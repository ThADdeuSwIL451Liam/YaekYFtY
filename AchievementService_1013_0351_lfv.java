// 代码生成时间: 2025-10-13 03:51:26
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.repository.AchievementRepository;
import com.example.demo.model.Achievement;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling achievements.
 */
@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    /**
     * Constructor for the AchievementService.
# NOTE: 重要实现细节
     * @param achievementRepository the achievement repository to use
     */
    @Autowired
# 增强安全性
    public AchievementService(AchievementRepository achievementRepository) {
# TODO: 优化性能
        this.achievementRepository = achievementRepository;
    }

    /**
     * Retrieves all achievements.
# 优化算法效率
     * @return a list of all achievements
     */
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    /**
     * Retrieves an achievement by its identifier.
     * @param achievementId the ID of the achievement
     * @return the achievement if found, otherwise throws an exception
     */
    public Achievement getAchievementById(Long achievementId) {
        return achievementRepository.findById(achievementId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found with id: " + achievementId));
    }

    /**
     * Saves a new achievement or updates an existing one.
     * @param achievement the achievement to save or update
     * @return the saved or updated achievement
# 优化算法效率
     */
    public Achievement saveAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    /**
     * Deletes an achievement by its identifier.
     * @param achievementId the ID of the achievement to delete
# 扩展功能模块
     */
# TODO: 优化性能
    public void deleteAchievement(Long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found with id: " + achievementId));
        achievementRepository.delete(achievement);
    }
# TODO: 优化性能
}
