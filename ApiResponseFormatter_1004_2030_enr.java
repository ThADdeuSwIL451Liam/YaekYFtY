// 代码生成时间: 2025-10-04 20:30:42
package com.example.demo.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
# 优化算法效率
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.exception.CustomException;
import com.example.demo.model.ApiResponse;

@RestControllerAdvice
public class ApiResponseFormatter {

    private final ObjectMapper objectMapper;
# TODO: 优化性能

    public ApiResponseFormatter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * This method is called when an exception occurs and formats the response accordingly.
     *
     * @param ex The exception that occurred.
     * @return A ResponseEntity with the formatted error response.
# TODO: 优化性能
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus("error");
        apiResponse.setMessage(ex.getMessage());
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(apiResponse), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
# 改进用户体验
    }

    /**
     * This method is called for custom exceptions and formats the response accordingly.
     *
     * @param ex The custom exception that occurred.
     * @return A ResponseEntity with the formatted error response.
# TODO: 优化性能
     */
# TODO: 优化性能
    @ExceptionHandler(CustomException.class)
# 扩展功能模块
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus("error");
        apiResponse.setMessage(ex.getMessage());
        try {
            return new ResponseEntity<>(objectMapper.writeValueAsString(apiResponse), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
# 改进用户体验
            return new ResponseEntity<>("Error processing JSON for custom exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }
# FIXME: 处理边界情况
    }
}
