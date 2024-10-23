package com.example.inventory_system.repository;

import com.example.inventory_system.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    void deleteByCategoryId(int categoryId);
}
