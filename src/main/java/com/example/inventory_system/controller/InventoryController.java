package com.example.inventory_system.controller;

import com.example.inventory_system.model.CategoryEntity;
import com.example.inventory_system.model.ProductEntity;
import com.example.inventory_system.repository.ProductRepository;
import com.example.inventory_system.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductEntity productEntity){
        return inventoryService.addProduct(productEntity);
    }
    @PatchMapping("/updateStock")
    public ResponseEntity<String> updateStock(@RequestBody ProductEntity productEntity) {
        String result = inventoryService.updateStock(productEntity);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable int id) {
        ProductEntity product = inventoryService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = inventoryService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/category/{categoryType}")
    public ResponseEntity<List<ProductEntity>> getProductsByCategory(@PathVariable String categoryType) {

        List<ProductEntity> products = inventoryService.getProductsByCategoryType(categoryType);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/sorted")
    public ResponseEntity<List<ProductEntity>> getSortedProductsByPrice() {
        List<ProductEntity> products = inventoryService.getSortedProductsByPrice();
        return ResponseEntity.ok(products);
    }
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategoryAndProducts(@PathVariable int categoryId) {
        inventoryService.deleteCategoryAndProducts(categoryId);
        return ResponseEntity.ok("Category and products deleted successfully.");
    }
}
