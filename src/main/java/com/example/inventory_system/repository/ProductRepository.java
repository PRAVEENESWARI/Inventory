package com.example.inventory_system.repository;

import com.example.inventory_system.model.CategoryEntity;
import com.example.inventory_system.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    List<ProductEntity> findByCategory_CategoryType(String categoryType);
    List<ProductEntity> findAllByOrderByPriceDesc();
    void deleteByCategory_CategoryId(int categoryId);
}
