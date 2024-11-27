package com.example.inventory_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name="productName", nullable = false)
    private String productName;

    @Column(name="price", nullable = false)
    private double price;

    @Column(name="quantity", nullable = false)
    private int quantity;

    @Column(name="description" , nullable = false)
    private String description;


    @OneToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId", nullable = false)
    private CategoryEntity category;

    public ProductEntity() {}

    public ProductEntity(String productName, double price, int quantity, String description, CategoryEntity category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getCategoryType() {
        return category.getCategoryType();
    }

}
