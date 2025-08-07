package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.entity.EmailLog;
import com.example.EmailMarketingSAAS.entity.Group;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import com.example.EmailMarketingSAAS.enums.EmailStatus;
import com.example.EmailMarketingSAAS.repository.EmailLoggerRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class EmailLoggerService {

    private final EmailLoggerRepo emailLoggerRepo;

    public boolean createEmailLog(EmailLog emailLog) {
        try {
            emailLoggerRepo.save(emailLog);
            return true;
        } catch (Exception e) {
            log.error("Error saving email campaign: " + emailLog, e);
            return false;
        }
    }

    public boolean changeStatus(UUID emailLogId, CampaignStatus status) {
        try{
            Optional<EmailLog> emailLog = emailLoggerRepo.findById(emailLogId);
            if(!emailLog.isPresent()){
                log.error("Email Log Not Found");
                return false;
            }
            emailLog.get().setStatus(EmailStatus.SUCCESS);
            emailLoggerRepo.save(emailLog.get());
            return true;
        } catch (Exception e) {
            log.error("Error saving email campaign: " + emailLogId, e);
            return false;
        }
    }
}
