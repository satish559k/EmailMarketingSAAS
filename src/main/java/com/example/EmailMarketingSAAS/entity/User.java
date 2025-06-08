package com.example.EmailMarketingSAAS.entity;

import com.example.EmailMarketingSAAS.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Role roles;
}
