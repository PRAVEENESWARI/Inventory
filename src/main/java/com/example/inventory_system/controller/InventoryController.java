package com.example.inventory_system.controller;

import com.example.inventory_system.model.CategoryEntity;
import com.example.inventory_system.model.LoginEntity;
import com.example.inventory_system.model.ProductEntity;
import com.example.inventory_system.repository.LoginRepository;
import com.example.inventory_system.service.InventoryService;
import com.example.inventory_system.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class InventoryController {

    private final InventoryService inventoryService;
    private final JwtService jwtService;

    public InventoryController(InventoryService inventoryService, JwtService jwtService){
        this.inventoryService=inventoryService;
        this.jwtService = jwtService;
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductEntity productEntity){
        return inventoryService.addProduct(productEntity);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginEntity loginEntity) {
        String response = inventoryService.register(loginEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginEntity loginEntity) {
        try {
            String token = inventoryService.login(loginEntity);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PatchMapping("/updateStock")
    public ResponseEntity<String> updateStock(@RequestBody ProductEntity productEntity)
    {
        try {
            String result = inventoryService.updateStock(productEntity);
            return ResponseEntity.ok(result);
        }
        catch(Exception e) {
            System.out.println("In UpdateStock given productId id missing :" + e.getMessage());
            throw null;
        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        try {
            ProductEntity product = inventoryService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = inventoryService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/getByCategory/{categoryType}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryType) {
        try {
            List<ProductEntity> products = inventoryService.getProductsByCategoryType(categoryType);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/products/sorted")
    public ResponseEntity<List<ProductEntity>> getSortedProductsByPrice() {
        List<ProductEntity> products = inventoryService.getSortedProductsByPrice();
        return ResponseEntity.ok(products);
    }
    @DeleteMapping("/deleteByCategory/{categoryId}")
    public ResponseEntity<?> deleteCategoryAndProducts(@PathVariable int categoryId) {
        try {
            inventoryService.deleteCategoryAndProducts(categoryId);
            return ResponseEntity.ok("Category and associated products deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/products/sortedAsc")
    public ResponseEntity<List<ProductEntity>> getSortedProductsByPriceAsc() {
        List<ProductEntity> products = inventoryService.getSortedProductsByPriceAsc();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/getCategory")
    public ResponseEntity<List<String>> getCategory(){
        List<String> products=inventoryService.getCategory();
        return ResponseEntity.ok(products);
    }

}
