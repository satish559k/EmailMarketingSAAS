package com.example.EmailMarketingSAAS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    private String uiGroupName;
    private String groupDescription;
    private String userName;
}
