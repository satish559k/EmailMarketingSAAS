package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.dto.EmailCampaignRequest;
import com.example.EmailMarketingSAAS.dto.EmailCampaignWithoutBodyDto;
import com.example.EmailMarketingSAAS.dto.GlobalApiResponse;
import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.entity.Group;
import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import com.example.EmailMarketingSAAS.mapper.EmailCampaignMapper;
import com.example.EmailMarketingSAAS.repository.EmailCampaignRepo;
import com.example.EmailMarketingSAAS.repository.GroupRepo;
import com.example.EmailMarketingSAAS.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor

public class EmailCampaignService {

    EmailCampaignRepo emailCampaignRepo;
    EmailCampaignMapper emailCampaignMapper;
    UserRepo userRepo;
    GroupRepo groupRepo;

    public EmailCampaign createEmailCampaign(EmailCampaignRequest emailCampaignRequest) {
        try{

            Optional<User> user = Optional.ofNullable(userRepo.findByName(emailCampaignRequest.getUserName()));
            Optional<Group> group = groupRepo.findById(emailCampaignRequest.getGroupId());
            if(user.isEmpty()){
                log.info("user is null");
                return null;
            }
            if(group.isEmpty()){
                log.info("group is null");
                return null;
            }

//            Optional<EmailCampaignWithoutBodyDto> isExist = emailCampaignRepo.isExistByUserIdAndGroupId(user.get().getId(),emailCampaignRequest.getGroupId());
//            if(isExist.isPresent() && isExist.get().getScheduledTime().equals(emailCampaignRequest.getScheduledTime())
//                && isExist.get().getScheduledTime().isBefore(emailCampaignRequest.getScheduledTime())){
//                return emailCampaignMapper.EmailCampaignWithoutBodyDtoToEmailCampaign(isExist.get());
//            }



            EmailCampaign emailCampaign = EmailCampaign.builder()
                    .body(emailCampaignRequest.getBody())
                    .subject(emailCampaignRequest.getSubject())
                    .scheduledTime(emailCampaignRequest.getScheduledTime())
                    .group(group.get())
                    .user(user.get())
                    .build();
           try {
               return  emailCampaignRepo.save(emailCampaign);
           }catch (Exception e){
               log.error("Error saving email campaign");
               return null;
           }

        } catch (Exception e) {
            log.error("Exception while creating email campaign");
            log.error(e.getMessage());
            return null;
        }

    }

    public Optional<EmailCampaign> getEmailCampaignById(UUID id) {
        try {
            Optional<EmailCampaign> emailCampaigns = emailCampaignRepo.findById(id);

            if(emailCampaigns.isEmpty()){
                log.info("email campaign is null");
                return Optional.empty();
            }
            return emailCampaigns;

        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Boolean> setEmailCampaignStatus(EmailCampaign emailCampaign) {
        try {
            emailCampaign.setStatus(CampaignStatus.SENT);
            emailCampaignRepo.save(emailCampaign);
            return Optional.of(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.of(false);
        }
    }
}
