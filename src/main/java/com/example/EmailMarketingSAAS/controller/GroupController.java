package com.example.EmailMarketingSAAS.controller;

import com.example.EmailMarketingSAAS.dto.GlobalApiResponse;
import com.example.EmailMarketingSAAS.dto.GroupRequest;
import com.example.EmailMarketingSAAS.service.GroupService;
import com.example.EmailMarketingSAAS.util.GlobalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RequestMapping("group")
@AllArgsConstructor
@Slf4j
@RestController
public class GroupController {
    //private final GlobalResponse globalResponse;
    private final GroupService groupService;

    @PostMapping("/get")
    public ResponseEntity<GlobalApiResponse<Object>> getGroup(@RequestBody GroupRequest groupName) {
        try{
            return GlobalResponse.success(HttpStatus.OK.value(),"Success",groupService.getGroup(groupName.getUiGroupName().replaceAll("\\s","").toLowerCase(Locale.ROOT)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.NO_CONTENT.value(), "Group name is null");
        }
    }

    @GetMapping()
    public ResponseEntity<GlobalApiResponse<Object>> getGroupById(@RequestParam UUID id) {
        try{
            //Here id is group ID
            return GlobalResponse.success(HttpStatus.OK.value(),"Success",groupService.getGroup(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.NO_CONTENT.value(), "Group name is null");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<GlobalApiResponse<Object>> CreateGroup(@RequestBody GroupRequest group) {
        try{
            return GlobalResponse.success(HttpStatus.OK.value(),"Success",groupService.createGroup(group));
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
