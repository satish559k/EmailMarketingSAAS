package com.example.EmailMarketingSAAS.mapper;

import com.example.EmailMarketingSAAS.dto.EmailCampaignRequest;
import com.example.EmailMarketingSAAS.dto.EmailCampaignWithoutBodyDto;
import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailCampaignMapper {
    EmailCampaign EmailCampaignWithoutBodyDtoToEmailCampaign(EmailCampaignWithoutBodyDto emailCampaignWithoutBodyDto);
}
