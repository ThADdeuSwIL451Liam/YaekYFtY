// 代码生成时间: 2025-08-14 08:11:54
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

// 主题切换服务
@Service
public class ThemeService {

    private static final String THEME_KEY = "theme";

    // 切换主题
    public String switchTheme(String theme) {
        if (theme == null || theme.isEmpty()) {
            throw new IllegalArgumentException("Theme cannot be null or empty");
        }
        return theme;
    }

    // 异常处理方法
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return "Error: Invalid theme. Please choose a valid theme.";
    }
}

@RestController
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    // HTTP GET请求处理主题切换
    @GetMapping("/theme")
    public String handleThemeChange(HttpSession session, @RequestParam String theme) {
        try {
            String currentTheme = themeService.switchTheme(theme);
            session.setAttribute(THEME_KEY, currentTheme);
            return "Theme switched to: " + currentTheme;
        } catch (IllegalArgumentException e) {
            return themeService.handleIllegalArgumentException(e);
        }
    }
}