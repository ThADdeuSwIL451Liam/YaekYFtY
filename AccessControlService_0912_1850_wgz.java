// 代码生成时间: 2025-09-12 18:50:48
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
# TODO: 优化性能
public class AccessControlService {

    /**
     * 检查用户是否有权限访问特定资源
     *
     * @param permission 需要的权限
     * @return Authentication对象，包含用户详细信息
     */
    @PreAuthorize("hasAuthority(#permission)")
    public Authentication checkPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied");
# TODO: 优化性能
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(permission))) {
# FIXME: 处理边界情况
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
# 改进用户体验
        return authentication;
    }

    /**
     * 一个示例方法，展示如何使用权限控制
     *
     * @return 字符串消息
     */
    public String executeProtectedOperation() {
        return "Operation executed successfully";
    }
}
