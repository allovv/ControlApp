package com.springapp.controllers.customers;

import com.springapp.entities.FolderEntity;
import com.springapp.entities.IssueEntity;
import com.springapp.entities.UserEntity;
import com.springapp.services.FolderRepoService;
import com.springapp.services.IssueRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserBasketController {
    //-------------------------------------------------------------
    @Autowired
    private FolderRepoService folderRepoService;
    @Autowired
    private IssueRepoService issueRepoService;

    //-------------------------------------------------------------
    /**
     * Показать корзину пользователя
     * GET
     */
    @GetMapping("/user/basket")
    public String getBasket(@AuthenticationPrincipal UserEntity userEntity, Map<String, Object> model) {
        //Add attributes
        model.put("userEntity", userEntity);
        model.put("folders", folderRepoService.findAllByCreatorId(userEntity.getId()));
        model.put("issues", getAllDeletedIssues(userEntity.getId()));

        return "user-basket.html";
    }

    //-------------------------------------------------------------
    private List<IssueEntity> getAllDeletedIssues(Long userId) {
        List<FolderEntity> userFolders = folderRepoService.findAllByCreatorId(userId);
        ArrayList<IssueEntity> deletedIssues = new ArrayList<>();

        for (FolderEntity folder : userFolders) {
            List<IssueEntity> issues = issueRepoService.findAllByFolderIdAndStatus(folder.getId(), IssueEntity.IssueStatus.DELETE);
            deletedIssues.addAll(issues);
        }

        return deletedIssues;
    }
}
