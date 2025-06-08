package com.example.EmailMarketingSAAS.util;

import com.example.EmailMarketingSAAS.dto.GlobalApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GlobalResponse {

    public static<T> ResponseEntity<GlobalApiResponse<T>> success(int status, String message, T data){
        GlobalApiResponse<T> res = new GlobalApiResponse<>(status,message,data);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public static<T> ResponseEntity<GlobalApiResponse<T>> failed(int status,String message){
        GlobalApiResponse<T> res = new GlobalApiResponse<>(status,message,null);
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
