package com.example.inventory_system.repository;

import com.example.inventory_system.model.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity,Integer> {
    boolean existsByUserName(String userName);
    LoginEntity findByUserNameAndPassword(String userName, String password);

}
