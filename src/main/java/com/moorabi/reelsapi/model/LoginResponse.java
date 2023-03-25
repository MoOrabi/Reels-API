package com.moorabi.reelsapi.model;

import lombok.Data;

@Data
public class LoginResponse {
    public LoginResponse(String token) {
        this.token = token;
    }

    private String token;
}
