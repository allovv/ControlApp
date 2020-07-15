package com.springapp.controllers.customers;

import com.springapp.entities.FolderEntity;
import com.springapp.entities.IssueEntity;
import com.springapp.entities.UserEntity;
import com.springapp.services.FolderRepoService;
import com.springapp.services.IssueRepoService;
import com.springapp.services.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.springapp.controllers.ControllersTools.getUserSecurity;

@Controller
public class AdminController {
    //-------------------------------------------------------------
    @Autowired
    UserRepoService userRepoService;
    @Autowired
    FolderRepoService folderRepoService;
    @Autowired
    IssueRepoService issueRepoService;

    //-------------------------------------------------------------

    /**
     * Получение шаблона страницы администратора
     * GET
     */
    @GetMapping("/admin")
    public String adminUsers(Map<String, Object> model) {
        UserEntity userEntity = getUserSecurity();

        //TODO: можно оптимизировать

        //количество пользователей
        List<UserEntity> userEntities = userRepoService.findAll();
        model.put("userEntity", userEntity);
        model.put("users", userEntities);
        model.put("numUsers", userEntities.size());

        //количество папок
        List<FolderEntity> folderEntities = folderRepoService.findAll();
        model.put("numFolders", folderEntities.size());

        HashMap<String, Long> numFoldersMap = new HashMap<>();
        for (UserEntity user : userEntities) {
            numFoldersMap.put(user.getUsername(), (long) folderRepoService.findAllByCreatorId(user.getId()).size());
        }
        model.put("numFoldersMap", numFoldersMap);

        //количество задач
        List<IssueEntity> issueEntities = issueRepoService.findAll();
        model.put("numIssues", issueEntities.size());
        HashMap<String, Long> numIssuesMap = new HashMap<>();
        for (UserEntity user : userEntities) {
            Long counter = 0L;
            for (FolderEntity folderEntity : folderRepoService.findAllByCreatorId(user.getId())) {
                counter += issueRepoService.findAllByFolderId(folderEntity.getId()).size();
            }
            numIssuesMap.put(user.getUsername(), counter);
        }
        model.put("numIssuesMap", numIssuesMap);

        return "admin";
    }

}
