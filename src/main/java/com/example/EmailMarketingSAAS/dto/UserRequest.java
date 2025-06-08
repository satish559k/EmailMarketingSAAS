package com.example.EmailMarketingSAAS.dto;

import com.example.EmailMarketingSAAS.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Role roles;
}
