package com.cryptotracker.portfolio.DTO;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    public String name;
    public String email;
    public String password;
}
