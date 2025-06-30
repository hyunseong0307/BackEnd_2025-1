package com.example.bcsd.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    private String account;
    private String password;
}