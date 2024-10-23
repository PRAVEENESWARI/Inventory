package com.example.inventory_system.service;

import com.example.inventory_system.model.CategoryEntity;
import com.example.inventory_system.model.ProductEntity;
import com.example.inventory_system.repository.CategoryRepository;
import com.example.inventory_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileSystemNotFoundException;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public String addProduct(ProductEntity productEntity){
        categoryRepository.save(productEntity.getCategory());
        productEntity.setPrice(productEntity.getPrice()*productEntity.getQuantity());
        productRepository.save(productEntity);
        return "product added successfully";
    }

    public String updateStock(ProductEntity productEntity) {
        int productId = productEntity.getProductId();
        int quantity=productEntity.getQuantity();
        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new FileSystemNotFoundException("Product not found"));
        existingProduct.setPrice((existingProduct.getPrice()/existingProduct.getQuantity())*(quantity+existingProduct.getQuantity()));
        existingProduct.setQuantity(existingProduct.getQuantity()+quantity);
        productRepository.save(existingProduct);
        return "Update stock successfully";
    }
    public ProductEntity getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new FileSystemNotFoundException("Product not found with id: " + productId));
    }
    public List<ProductEntity> getAllProducts()
    {
        return productRepository.findAll();
    }
    public List<ProductEntity> getProductsByCategoryType(String categoryType) {
        return productRepository.findByCategory_CategoryType(categoryType);
    }

    public List<ProductEntity> getSortedProductsByPrice() {
        return productRepository.findAllByOrderByPriceDesc();
    }
    @Transactional
    public void deleteCategoryAndProducts(int categoryId) {
        productRepository.deleteByCategory_CategoryId(categoryId);
        categoryRepository.deleteByCategoryId(categoryId);
    }
}
