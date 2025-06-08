package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.dto.GroupEmailRequest;
import com.example.EmailMarketingSAAS.entity.Group;
import com.example.EmailMarketingSAAS.entity.GroupOfEmail;
import com.example.EmailMarketingSAAS.repository.GroupOfEmailRepo;
import com.example.EmailMarketingSAAS.repository.GroupRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class GroupOfEmailService {

    private final GroupOfEmailRepo groupOfEmailRepo;
    private final GroupRepo groupRepo;

        public Boolean createGroupEmail(GroupEmailRequest groupOfEmail) {
        try{
            GroupOfEmail groupOfEmailEntity = new GroupOfEmail();
            groupOfEmailEntity.setEmail(groupOfEmail.getEmail());
            Optional<Group> group = groupRepo.findById(groupOfEmail.getGroupId());
            groupOfEmailEntity.setGroup(group.get());
            groupOfEmailRepo.save(groupOfEmailEntity);
            return true;
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("Internal Error while creating group of email", e);

        }
    }

    public List<GroupOfEmail> findGroupOfEmailById(UUID groupId) {
        try{
            List<GroupOfEmail> groupOfEmail =  groupOfEmailRepo.findBygroupId(groupId);
            return groupOfEmail;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
