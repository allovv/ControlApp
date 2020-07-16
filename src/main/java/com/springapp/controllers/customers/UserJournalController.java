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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserJournalController {
    //-------------------------------------------------------------
    @Autowired
    private FolderRepoService folderRepoService;
    @Autowired
    private IssueRepoService issueRepoService;

    //-------------------------------------------------------------
    /**
     * Показать журнал пользователя
     * GET
     */
    @GetMapping("/user/journal")
    public String getUserJournal(@AuthenticationPrincipal UserEntity userEntity, Map<String, Object> model) {
        //Add attributes
        model.put("userEntity", userEntity);
        model.put("folders", folderRepoService.findAllByCreatorId(userEntity.getId()));

        List<IssueEntity> doneIssues = getAllDoneIssues(userEntity.getId());
        model.put("numDoneIssues", doneIssues.size());

        model.put("doneFolderIssues", getAllDoneIssuesWithFolders(userEntity.getId()));

        return "user-journal";
    }

    //-------------------------------------------------------------
    private List<IssueEntity> getAllDoneIssues(Long userId) {
        List<IssueEntity> issues = issueRepoService.findAllByCreatorIdAndStatus(userId, IssueEntity.IssueStatus.COMMON);
        ArrayList<IssueEntity> doneIssues = new ArrayList<>();
        for (IssueEntity issue: issues) {
            if (issue.getDone()) {
                doneIssues.add(issue);
            }
        }
        return doneIssues;
    }

    private HashMap<String, List<IssueEntity>> getAllDoneIssuesWithFolders(Long userId) {

        HashMap<String, List<IssueEntity>> doneFolderIssues = new HashMap<>();
        List<FolderEntity> userFolders = folderRepoService.findAllByCreatorId(userId);
        for (FolderEntity folder : userFolders) {
            List<IssueEntity> allDone = issueRepoService.findAllByFolderIdAndDone(folder.getId(), true);
            if (!(allDone.size() == 0)) {
                doneFolderIssues.put(folder.getName(), allDone);
            }
        }

        return  doneFolderIssues;
    }
}
