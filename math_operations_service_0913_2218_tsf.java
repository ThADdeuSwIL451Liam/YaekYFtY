// 代码生成时间: 2025-09-13 22:18:17
package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@Service
public class MathOperationsService {

    // Adds two numbers
    public Double add(Double a, Double b) {
        return a + b;
    }

    // Subtracts two numbers
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    // Multiplies two numbers
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    // Divides two numbers
    public Double divide(Double a, Double b) {
        // Error handling for division by zero
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return a / b;
    }
}

@RestController
class MathOperationsController {

    private final MathOperationsService mathOperationsService;

    MathOperationsController(MathOperationsService mathOperationsService) {
        this.mathOperationsService = mathOperationsService;
    }

    @GetMapping("/add")
    public ResponseEntity<Double> add(@RequestParam Double number1, @RequestParam Double number2) {
        return new ResponseEntity<>(mathOperationsService.add(number1, number2), HttpStatus.OK);
    }

    @GetMapping="/subtract")
    public ResponseEntity<Double> subtract(@RequestParam Double number1, @RequestParam Double number2) {
        return new ResponseEntity<>(mathOperationsService.subtract(number1, number2), HttpStatus.OK);
    }

    @GetMapping("/multiply")
    public ResponseEntity<Double> multiply(@RequestParam Double number1, @RequestParam Double number2) {
        return new ResponseEntity<>(mathOperationsService.multiply(number1, number2), HttpStatus.OK);
    }

    @GetMapping("/divide")
    public ResponseEntity<Double> divide(@RequestParam Double number1, @RequestParam Double number2) {
        try {
            return new ResponseEntity<>(mathOperationsService.divide(number1, number2), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}