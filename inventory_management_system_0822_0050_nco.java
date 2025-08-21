// 代码生成时间: 2025-08-22 00:50:04
package com.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api/inventory")
public class InventoryManagementSystem {

    private final Map<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementSystem.class, args);
    }

    // 添加库存项
    @PostMapping("/add")
    public ResponseEntity<String> addInventoryItem(@RequestBody Map<String, Integer> item) {
        try {
            String itemName = item.keySet().iterator().next();
            Integer quantity = item.values().iterator().next();
            inventory.put(itemName, inventory.getOrDefault(itemName, 0) + quantity);
            return ResponseEntity.ok("Item added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding item.");
        }
    }

    // 更新库存项
    @PostMapping("/update/{itemName}")
    public ResponseEntity<String> updateInventoryItem(@PathVariable String itemName, @RequestBody Integer quantity) {
        try {
            if (inventory.containsKey(itemName)) {
                inventory.put(itemName, quantity);
                return ResponseEntity.ok("Item updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating item.");
        }
    }

    // 获取库存项
    @GetMapping("/items/{itemName}")
    public ResponseEntity<Map<String, Integer>> getInventoryItem(@PathVariable String itemName) {
        if (inventory.containsKey(itemName)) {
            return ResponseEntity.ok(new HashMap<>() {{ put(itemName, inventory.get(itemName)); }});
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 获取所有库存项
    @GetMapping("/items")
    public ResponseEntity<Map<String, Integer>> getAllInventoryItems() {
        return ResponseEntity.ok(inventory);
    }
}
