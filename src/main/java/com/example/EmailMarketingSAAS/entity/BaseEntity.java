package com.example.EmailMarketingSAAS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Column(nullable = false,name ="created_at",updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PostUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
