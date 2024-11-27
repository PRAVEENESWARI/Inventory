package com.example.inventory_system.service;

import com.example.inventory_system.model.LoginEntity;
import com.example.inventory_system.model.ProductEntity;
import com.example.inventory_system.model.TokenEntity;
import com.example.inventory_system.repository.CategoryRepository;
import com.example.inventory_system.repository.LoginRepository;
import com.example.inventory_system.repository.ProductRepository;
//import com.example.inventory_system.util.JwtUtil;
import com.example.inventory_system.repository.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenRepository tokenRepository;
//    private final LoginRepository loginRepository;
//    private final JwtService jwtService;

    public InventoryService(LoginRepository loginRepository, JwtService jwtService) {
        this.loginRepository = loginRepository;
        this.jwtService = jwtService;
    }

    public String addProduct(ProductEntity productEntity) {
        try {
            if (productEntity.getCategory() != null && productEntity.getPrice() >0 && productEntity.getQuantity()>0) {
                categoryRepository.save(productEntity.getCategory());
            }else {
                return "Enter a valid input";
            }
            productEntity.setPrice(productEntity.getPrice() * productEntity.getQuantity());
            productRepository.save(productEntity);
            return "Product added successfully";

        } catch (Exception e) {
            return "Error adding product: " + e.getMessage();
        }
    }
    public String register(LoginEntity loginEntity) {
        if (loginRepository.existsByUserName(loginEntity.getUserName())) {
            return "Username is already taken, please try again.";
        }
        loginRepository.save(loginEntity);
        return "Registration successful";
    }

    public String login(LoginEntity loginEntity) {
        String userName = loginEntity.getUserName();
        String password = loginEntity.getPassword();

        // Find the existing user by username and password
        LoginEntity existingUser = loginRepository.findByUserNameAndPassword(userName, password);

        if (existingUser != null) {
            // Try to find the existing token for the user
            TokenEntity existingTokenEntity = tokenRepository.findByLogin(existingUser);

            String token;
            String expiryTime;

            // Check if there is an existing token and if it's expired
            if (existingTokenEntity != null) {
                // Check if the token is expired
                if (isTokenExpired(existingTokenEntity.getExpiredTime())) {
                    // Generate a new token
                    token = jwtService.generateToken(userName);
                    expiryTime = calculateExpiryTime();

                    // Update the existing token in the database
                    existingTokenEntity.setToken(token);
                    existingTokenEntity.setExpiredTime(expiryTime);
                    tokenRepository.save(existingTokenEntity);
                } else {
                    // Return the existing token if it's not expired
                    token = existingTokenEntity.getToken();
                    expiryTime = existingTokenEntity.getExpiredTime();
                }
            } else {
                // If no token exists, create a new token
                token = jwtService.generateToken(userName);
                expiryTime = calculateExpiryTime();

                // Create a new token entity and save it
                TokenEntity newTokenEntity = new TokenEntity(token, expiryTime, existingUser);
                tokenRepository.save(newTokenEntity);
            }

            // Return the generated or existing token
            return token;
        } else {
            throw new IllegalArgumentException("Invalid username or password. Please sign up if you don’t have an account.");
        }
    }
    private boolean isTokenExpired(String expiredTime) {
        try {
            // Convert the expiredTime (String) to Date
            Date expirationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiredTime);

            // Check if the current time is after the expiration date
            return expirationDate.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String calculateExpiryTime() {
        Date expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 1); // 24 hours from now
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(expiryDate);
    }

    public String updateStock(ProductEntity productEntity) {
        try {
            int productId = productEntity.getProductId();
            int quantity = productEntity.getQuantity();
            ProductEntity existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            existingProduct.setPrice((existingProduct.getPrice() / existingProduct.getQuantity()) * (quantity + existingProduct.getQuantity()));
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            productRepository.save(existingProduct);
            return "Update stock successfully";
        }
        catch(Exception e)
        {
            return "In UpdateStock given productId id missing : "+productEntity.getProductId();
        }
    }
    public ProductEntity getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
    }

    public List<ProductEntity> getAllProducts()
    {
        return productRepository.findAll();
    }
    public List<ProductEntity> getProductsByCategoryType(String categoryType) {
        List<ProductEntity> productEntityList = productRepository.findByCategory_CategoryType(categoryType);
        if (!productEntityList.isEmpty()) {
            return productEntityList;
        } else {
            throw new IllegalArgumentException("No products found for category type: " + categoryType);
        }
    }

    public List<ProductEntity> getSortedProductsByPrice() {
        return productRepository.findAllByOrderByPriceDesc();
    }
    public List<ProductEntity> getSortedProductsByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }
    @Transactional
    public void deleteCategoryAndProducts(int categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
        productRepository.deleteByCategory_CategoryId(categoryId);
        categoryRepository.deleteByCategoryId(categoryId);
    }
    public List<ProductEntity> getProductByCategoryId(int categoryId) {
        List<ProductEntity> productEntityList = productRepository.findByCategory_CategoryId(categoryId);
        if (!productEntityList.isEmpty()) {
            categoryRepository.findAll();
            return productEntityList;
        } else {
            throw new IllegalArgumentException("No category Id found for Product : " + categoryId);
        }
    }

    public List<String> getCategory() {
        return categoryRepository.findDistinctCategoryTypes();
    }
}
