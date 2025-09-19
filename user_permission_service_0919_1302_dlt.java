// 代码生成时间: 2025-09-19 13:02:45
package com.example.demo.service;

import com.example.demo.exception.PermissionException;
import com.example.demo.model.Permission;
import com.example.demo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

// 用户权限管理服务组件
@Service
public class UserPermissionService {

    // 注入权限仓库
    @Autowired
    private PermissionRepository permissionRepository;

    // 获取所有权限
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // 根据ID获取权限
    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionException("Permission not found with id: " + id));
    }

    // 创建权限
    @Transactional
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    // 更新权限
    @Transactional
    public Permission updatePermission(Long id, Permission permissionDetails) {
        Permission permission = getPermissionById(id);
        permission.setName(permissionDetails.getName());
        permission.setDescription(permissionDetails.getDescription());
        return permissionRepository.save(permission);
    }

    // 删除权限
    @Transactional
    public void deletePermission(Long id) {
        Permission permission = getPermissionById(id);
        permissionRepository.delete(permission);
    }

    // 自定义异常类
    class PermissionException extends RuntimeException {

        PermissionException(String message) {
            super(message);
        }
    }
}