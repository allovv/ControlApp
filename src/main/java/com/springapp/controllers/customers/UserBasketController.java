package com.springapp.controllers.customers;

import com.springapp.entities.IssueEntity;
import com.springapp.entities.UserEntity;
import com.springapp.services.FolderRepoService;
import com.springapp.services.IssueRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    /**
     * Очистить корзину
     * POST
     */
    @PostMapping("/user/basket/clear")
    public String clearBasket(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        for (IssueEntity deletedIssue : getAllDeletedIssues(userEntity.getId())) {
            issueRepoService.deleteById(deletedIssue.getId());
        }
        return "redirect:/user/basket";
    }

    /**
     * Удалить конкретную задачу из корзины
     * POST
     */
    @PostMapping("/user/basket/clear/{issueId}")
    public String deleteIssueFromBasket(@PathVariable("issueId") Long issueId, Model model) {
        issueRepoService.deleteById(issueId);
        return "redirect:/user/basket";
    }

    /**
     * Восстановить конкретную задачу из корзины
     * POST
     */
    @PostMapping("/user/basket/repair/{issueId}")
    public String repairIssueFromBasket(@PathVariable("issueId") Long issueId,
                                        @ModelAttribute IssueEntity issueToRepair,
                                        Model model) {
        if (issueToRepair.getFolderId() == null || issueToRepair.getCreatorId() == null || issueToRepair.getId() == null) {
            //ошибка восстановления задачи
            return "redirect:/user/basket";
        }
        issueRepoService.save(issueToRepair);
        return "redirect:/user/basket";
    }

    //-------------------------------------------------------------
    private List<IssueEntity> getAllDeletedIssues(Long userId) {
        return issueRepoService.findAllByCreatorIdAndStatus(userId, IssueEntity.IssueStatus.DELETE);
    }
}
