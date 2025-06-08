package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.dto.GroupRequest;
import com.example.EmailMarketingSAAS.entity.Group;
import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.repository.GroupRepo;
import com.example.EmailMarketingSAAS.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepo groupRepo;
    private final UserRepo userRepo;

    //Create a method which will return all group based on userId (User Id in query parameter)
    //path-> http://localhost:8080/group/get?userId=6caed20f-c21c-4ebc-b942-ed3b7845e8d4

    public Group getGroup(String groupName) {
        try{
            if(groupName != null){
                groupName = groupName.trim();
                Group group = groupRepo.findByGroupName(groupName);
                return group;
            }else {
                log.info("groupName is null");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Getting Group record failed", e);
        }
    }

    public Group getGroup(UUID groupID) {
        try{
            if(groupID != null){
                Optional<Group> group = groupRepo.findById(groupID);
                return group.get();
            }else {
                log.info("groupName is null");
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Getting Group record failed", e);
        }
    }

    public boolean createGroup(GroupRequest group) {
        try {
            if (group!=null){
                User user = userRepo.findByName(group.getUserName());
                if (user!=null){
                    Group newGroup = new Group();
                    newGroup.setUiGroupName(group.getUiGroupName());
                    newGroup.setGroupName(group.getUiGroupName().replaceAll("\\s","").toLowerCase(Locale.ROOT));
                    newGroup.setGroupDescription(group.getGroupDescription());
                    newGroup.setUser(user);
                    groupRepo.save(newGroup);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Group creation failed", e);

        }
    }
}
