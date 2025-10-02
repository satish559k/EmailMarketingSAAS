package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.entity.EmailLog;
import com.example.EmailMarketingSAAS.entity.GroupOfEmail;
import com.example.EmailMarketingSAAS.enums.EmailStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final GroupOfEmailService groupOfEmailService;
    private final EmailCampaignService emailCampaignService;
    private final EmailLoggerService emailLoggerService;
    //private final //email logger

    public void sendEmailCampaign(EmailCampaign emailCampaign) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        List<GroupOfEmail> ListOfEmail = groupOfEmailService.findGroupOfEmailById(emailCampaign.getGroup().getId());
        for(GroupOfEmail groupOfEmail : ListOfEmail){
           try {
               helper.setTo(groupOfEmail.getEmail());
               helper.setSubject(emailCampaign.getSubject());
               helper.setText(emailCampaign.getBody(), true);
               mailSender.send(message);
               log.info("email campaign Send Successfully to"+groupOfEmail.getEmail());
           }catch (MessagingException e){
               EmailLog emailLog = new EmailLog();
               emailLog.setEmailID(groupOfEmail.getEmail());
               emailLog.setErrorMessage(e.getMessage());
               emailLog.setStatus(EmailStatus.PENDING);
               emailLog.setSentDateTime(LocalDateTime.now());
               emailLog.setCampaign(emailCampaign);
               emailLoggerService.createEmailLog(emailLog);
               log.error(e.getMessage());
           }
        }
        emailCampaignService.setEmailCampaignStatus(emailCampaign);
    }
}
