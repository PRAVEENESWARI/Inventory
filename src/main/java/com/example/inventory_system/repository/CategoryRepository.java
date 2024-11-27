package com.example.inventory_system.repository;

import com.example.inventory_system.model.CategoryEntity;
import com.example.inventory_system.model.ProductEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    void deleteByCategoryId(int categoryId);
    @Query("SELECT DISTINCT c.categoryType FROM CategoryEntity c")
    List<String> findDistinctCategoryTypes();

}
