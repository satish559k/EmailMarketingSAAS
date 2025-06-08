package com.example.EmailMarketingSAAS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalApiResponse<T> {
    public int status;
    public String message;
    public T data;
}
