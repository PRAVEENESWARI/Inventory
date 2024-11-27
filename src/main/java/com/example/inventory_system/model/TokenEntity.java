package com.example.inventory_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "loginId", referencedColumnName = "loginId", nullable = false)
    private LoginEntity login;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expiredTime", nullable = false)
    private String expiredTime;

    public TokenEntity() {
    }

    public TokenEntity(String token, String expiredTime, LoginEntity login) {
        this.token = token;
        this.expiredTime = expiredTime;
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public LoginEntity getLogin() {
        return login;
    }

    public void setLogin(LoginEntity login) {
        this.login = login;
    }
}