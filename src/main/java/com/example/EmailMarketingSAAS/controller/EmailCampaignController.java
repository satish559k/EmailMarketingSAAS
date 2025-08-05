package com.example.EmailMarketingSAAS.controller;

import com.example.EmailMarketingSAAS.dto.EmailCampaignRequest;
import com.example.EmailMarketingSAAS.dto.GlobalApiResponse;
import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.service.EmailCampaignService;
import com.example.EmailMarketingSAAS.util.GlobalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("emailcampaign")
@RestController
@Slf4j
@AllArgsConstructor
public class EmailCampaignController {

    private final EmailCampaignService emailCampaignService;

    @PostMapping()
    public ResponseEntity<GlobalApiResponse<Object>> getEmailCampaignService(@RequestParam UUID id) {
        try{
            return GlobalResponse.success(HttpStatus.OK.value(), "Success",emailCampaignService.getEmailCampaignById(id));
        } catch (Exception e) {
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @PostMapping("create")
    public ResponseEntity<GlobalApiResponse<Object>> createEmailCampaign(@RequestBody EmailCampaignRequest emailCampaign) {
        try{
            EmailCampaign result = emailCampaignService.createEmailCampaign(emailCampaign);
            if(result!=null) {
                return GlobalResponse.success(HttpStatus.CREATED.value(), "Success", result);
            }
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error creating email campaign or its already exist");
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
