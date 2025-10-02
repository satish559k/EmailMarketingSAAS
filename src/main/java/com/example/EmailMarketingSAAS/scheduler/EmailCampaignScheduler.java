package com.example.EmailMarketingSAAS.scheduler;

import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import com.example.EmailMarketingSAAS.repository.EmailCampaignRepo;
import com.example.EmailMarketingSAAS.service.EmailSenderService;
import com.example.EmailMarketingSAAS.service.RedisService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
@Transactional
public class EmailCampaignScheduler {
    private final EmailCampaignRepo emailCampaignRepo;
    private final EmailSenderService emailSenderService;
    private final RedisService redisService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void EmailScheduler() throws MessagingException {

        List<EmailCampaign> emailCampaigns = emailCampaignRepo.findByStatusAndScheduledTimeLessThan(CampaignStatus.PENDING,
                LocalDateTime.now());

        for (EmailCampaign emailCampaign : emailCampaigns) {
            sendEmail(emailCampaign);
        }


//         Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection()
//                .scan(ScanOptions.scanOptions().match("email:").count(100).build());
//
//
//        while (cursor.hasNext()) {
//            String key = new String(cursor.next());
//            String value = redisTemplate.opsForValue().get(key);
//        }
//
//        for (EmailCampaign emailCampaign : emailCampaigns) {
//            sendEmail(emailCampaign);
//        }

    }

//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 * * * * *")
    public void RedisSchedule() throws MessagingException {
        List<EmailCampaign> emailCampaigns = emailCampaignRepo.findByStatusAndScheduledTimeDate(CampaignStatus.PENDING, LocalDate.now());
        for (EmailCampaign emailCampaign : emailCampaigns) {
            redisService.SetValue("email:"+emailCampaign.getId().toString(),emailCampaign.getScheduledTime());
        }
    }

//    in this cron remove DB call and add redis call every min
//    there will be one more cron run on 12:00am every day to
//    get data and store in redis,after data used delete the data from redis
//

    public void sendEmail(EmailCampaign emailCampaign) throws MessagingException {
        emailSenderService.sendEmailCampaign(emailCampaign);
    }
}
