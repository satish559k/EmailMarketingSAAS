package com.example.EmailMarketingSAAS.mapper;

import com.example.EmailMarketingSAAS.dto.JwtTokenDto;
import com.example.EmailMarketingSAAS.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtTokenMapper {
    JwtTokenDto UserDetailsToJwtTokenDto(User user);
}
