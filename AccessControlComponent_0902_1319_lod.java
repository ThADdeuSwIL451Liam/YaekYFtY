// 代码生成时间: 2025-09-02 13:19:17
package com.example.demo.component;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Component
@RestController
@RequestMapping("/api/access-control")
public class AccessControlComponent {

    /**
     * 访问权限控制示例方法
     * 只有具有ROLE_ADMIN角色的用户才能访问此方法
     */
    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> adminOnlyMethod() {
        return ResponseEntity.ok("Welcome Admin!");
    }

    /**
     * 访问权限控制示例方法
     * 只有具有ROLE_USER角色的用户才能访问此方法
     */
    @GetMapping("/user-only")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> userOnlyMethod() {
        return ResponseEntity.ok("Welcome User!");
    }

    // 可以添加更多的访问权限控制方法

    // 错误处理方法
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleSecurityException(SecurityException e) {
        return ResponseEntity.status(403).body("Access Denied: You do not have permission to perform this action");
    }

}
