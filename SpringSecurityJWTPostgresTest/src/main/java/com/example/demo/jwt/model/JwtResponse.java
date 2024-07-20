package com.example.demo.jwt.model;

public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter e setter per il campo token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

