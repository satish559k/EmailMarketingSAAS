package com.example.EmailMarketingSAAS.dto;

import com.example.EmailMarketingSAAS.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {
    public String name;
    public Role roles;
    public UUID id;
}
