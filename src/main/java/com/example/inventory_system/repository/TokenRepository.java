package com.example.inventory_system.repository;

import com.example.inventory_system.model.LoginEntity;
import com.example.inventory_system.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    TokenEntity findByLogin(LoginEntity login);

    TokenEntity findByToken(String token);
}