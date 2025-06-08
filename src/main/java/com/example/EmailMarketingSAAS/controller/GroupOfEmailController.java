package com.example.EmailMarketingSAAS.controller;

import com.example.EmailMarketingSAAS.dto.GlobalApiResponse;
import com.example.EmailMarketingSAAS.dto.GroupEmailRequest;
import com.example.EmailMarketingSAAS.service.GroupOfEmailService;
import com.example.EmailMarketingSAAS.util.GlobalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("groupofemail")
@AllArgsConstructor
@RestController
@Slf4j
public class GroupOfEmailController {
    private final GroupOfEmailService groupOfEmailService;

    @PostMapping("create")
    public ResponseEntity<GlobalApiResponse<Object>> CreateGroup(@RequestBody GroupEmailRequest groupEmailRequest) {
        try{
            boolean result = groupOfEmailService.createGroupEmail(groupEmailRequest);
            if(result){
                return GlobalResponse.success(HttpStatus.OK.value(), "Success", "New group created");
            }
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while creating new group of email");
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

    }

    @GetMapping()
    public ResponseEntity<GlobalApiResponse<Object>> getGroup(@RequestParam UUID id) {
        try{
            return GlobalResponse.success(HttpStatus.OK.value(), "Success",groupOfEmailService.findGroupOfEmailById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
