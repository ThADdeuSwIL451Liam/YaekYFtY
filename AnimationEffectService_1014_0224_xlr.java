// 代码生成时间: 2025-10-14 02:24:22
package com.example.demo.service;
# NOTE: 重要实现细节

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
# FIXME: 处理边界情况
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
# NOTE: 重要实现细节
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.example.demo.exception.AnimationEffectNotFoundException;

@RestController
@RequestMapping("/api/animation-effects")
@Service
public class AnimationEffectService {

    private final AnimationEffectRepository animationEffectRepository;

    @Autowired
    public AnimationEffectService(AnimationEffectRepository animationEffectRepository) {
        this.animationEffectRepository = animationEffectRepository;
    }
# 改进用户体验

    // 获取所有动画效果
    @GetMapping
    public ResponseEntity<List<AnimationEffect>> getAllAnimationEffects() {
        return ResponseEntity.ok(animationEffectRepository.findAll());
# 增强安全性
    }

    // 根据ID获取动画效果
    @GetMapping("/{id}")
    public ResponseEntity<AnimationEffect> getAnimationEffectById(@PathVariable Long id) {
# 改进用户体验
        return animationEffectRepository.findById(id)
            .map(ResponseEntity::ok)
# NOTE: 重要实现细节
            .orElseThrow(() -> new AnimationEffectNotFoundException("Animation effect not found with id: " + id));
    }

    // 错误处理
# 添加错误处理
    @ExceptionHandler(AnimationEffectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAnimationEffectNotFoundException(AnimationEffectNotFoundException ex) {
        return ex.getMessage();
    }

    // 校验请求参数
# NOTE: 重要实现细节
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldError().getDefaultMessage();
# 优化算法效率
    }
}
# 添加错误处理
