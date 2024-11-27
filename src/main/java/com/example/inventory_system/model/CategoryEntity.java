package com.example.inventory_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Column(name="categoryType", nullable = false)
    private String categoryType;
    @Column(name = "brand", nullable = false)
    private String brand;

    public CategoryEntity(int categoryId, String categoryType, String brand) {
        this.categoryId = categoryId;
        this.categoryType = categoryType;
        this.brand = brand;
    }

    public CategoryEntity() {}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
