package com.springapp.controllers.customers;

import com.springapp.entities.UserEntity;
import com.springapp.services.FolderRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UserController {
    //-------------------------------------------------------------
    @Autowired
    private FolderRepoService folderRepoService;

    //-------------------------------------------------------------
    /**
     * Запрос по умолчанию для пользователя
     * GET
     */
    @GetMapping("/user")
    public String getUserView(@AuthenticationPrincipal UserEntity userEntity, Map<String, Object> model) {
        //Add attributes
        model.put("userEntity", userEntity);
        model.put("folders", folderRepoService.findAllByCreatorId(userEntity.getId()));

        return "user";
    }

}
