package com.example.bcsd.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupDto {
    private String account;
    private String password;
    private String confirmPassword;
}