package com.springapp.controllers.customers;

import com.springapp.entities.UserEntity;
import com.springapp.services.CollectionRepoService;
import com.springapp.services.FolderRepoService;
import com.springapp.services.IssueRepoService;
import com.springapp.services.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

import static com.springapp.controllers.ControllersTools.getUserSecurity;

@Controller
public class AdminController { //TODO: корректировать
    //-------------------------------------------------------------
    @Autowired
    UserRepoService userRepoService;
    @Autowired
    FolderRepoService folderRepoService;
    @Autowired
    CollectionRepoService collectionRepoService;
    @Autowired
    IssueRepoService issueRepoService;

    //-------------------------------------------------------------
    @GetMapping("/admin/users")
    public String adminUsers(Map<String, Object> model) {
        UserEntity userEntity = getUserSecurity();

        //Add attributes
        model.put("username", userEntity.getUsername());
        model.put("users", userRepoService.findAll());
        model.put("folders", folderRepoService.findAll());
        model.put("collections", collectionRepoService.findAll());
        model.put("issues", issueRepoService.findAll());

        return "adminUsers";
    }

    @GetMapping("/admin/users/{userId}")
    public String editUser(@PathVariable Long userId, Map<String, Object> model) {
        UserEntity userEntity = userRepoService.findById(userId);
        model.put("userToEdit", userEntity);
        return "adminUserEdit";
    }

    //-------------------------------------------------------------
    @GetMapping("/admin/folders")
    public String adminFolders(Map<String, Object> model) {
        UserEntity userEntity = getUserSecurity();

        //Add attributes
        model.put("username", userEntity.getUsername());
        model.put("users", userRepoService.findAll());
        model.put("folders", folderRepoService.findAll());
        model.put("collections", collectionRepoService.findAll());
        model.put("issues", issueRepoService.findAll());

        return "adminFolders";
    }
}
