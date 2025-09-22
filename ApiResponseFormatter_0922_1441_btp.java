// 代码生成时间: 2025-09-22 14:41:18
package com.example.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * API响应格式化工具，用于统一API响应格式
 */
public class ApiResponseFormatter {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    /**
     * 创建成功的API响应
     * 
     * @param data 响应数据
     * @return 格式化后的API响应
     */
    public static ResponseEntity<Map<String, Object>> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put(SUCCESS, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 创建错误的API响应
     * 
     * @param message 错误信息
     * @return 格式化后的API响应
     */
    public static ResponseEntity<Map<String, Object>> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR, message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
