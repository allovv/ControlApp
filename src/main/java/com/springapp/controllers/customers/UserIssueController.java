package com.springapp.controllers.customers;

import com.springapp.entities.IssueEntity;
import com.springapp.entities.UserEntity;
import com.springapp.services.FolderRepoService;
import com.springapp.services.IssueRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.springapp.controllers.ControllersTools.getFieldErrors;

@Controller
public class UserIssueController {
    //-------------------------------------------------------------
    @Autowired
    IssueRepoService issueRepoService;
    @Autowired
    FolderRepoService folderRepoService;
    //-------------------------------------------------------------
    /**
     * Редактирование задачи (получение станицы для редактирования)
     * GET
     */
    @GetMapping("/user/{folderId}/{issueId}/edit")
    public String editIssue(@AuthenticationPrincipal UserEntity userEntity,
                            @PathVariable("folderId") Long folderId,
                            @PathVariable("issueId") Long issueId,
                            Map<String, Object> model) {

        //Add attributes
        model.put("userEntity", userEntity);
        model.put("folders", folderRepoService.findAllByCreatorId(userEntity.getId()));

        model.put("folderId", folderId);
        model.put("currentIssue", issueRepoService.findById(issueId));

        return "user-edit-issue";
    }

    //-------------------------------------------------------------
    /**
     * Добавление задачи
     * POST
     */
    @PostMapping("/user/folder/issue")
    public String addIssue(@ModelAttribute @Valid IssueEntity issueEntity, BindingResult bindingResult,
                           @ModelAttribute("folderId") Long folderId,
                           HttpSession httpSession,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = getFieldErrors(bindingResult);
            for (FieldError fieldError : fieldErrors) {
                //redirect
                redirectAttributes.addFlashAttribute(fieldError.getField() + "AddIssueError", fieldError.getDefaultMessage());
            }

        } else {
            if (!issueRepoService.addIssue(issueEntity)) {
                //redirect
                redirectAttributes.addFlashAttribute("existAddIssueError", "Не передан идентификатор текущей области!");
            }

        }
        //перенаправление к /user/folders/{folderId}
        return "redirect:/user/folders/" + folderId;
    }

    /**
     * Редактирование задачи
     * POST
     */
    @PostMapping("/user/folder/issue/edit")
    public String saveEditIssue(@AuthenticationPrincipal UserEntity userEntity,
                                @ModelAttribute @Valid IssueEntity issueEntity, BindingResult bindingResult,
                                @ModelAttribute("folderId") Long folderId,
                                @ModelAttribute("issueId") Long issueId,
                                @ModelAttribute("checkboxValue") String checkboxValue,
                                HttpSession httpSession,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError: getFieldErrors(bindingResult)) {
                redirectAttributes.addFlashAttribute(fieldError.getField() + "EditIssueError", fieldError.getDefaultMessage());
                return "redirect:/user/" + folderId + "/" + issueId + "/edit"; //redirect к get запросу
            }
        }
        issueEntity.setDone("selected".equals(checkboxValue));
        if (issueRepoService.editIssueById(issueEntity, issueId)) {
            return "redirect:/user/folders/" + folderId;
        } else {
            return "redirect:/user/" + folderId + "/" + issueId + "/edit"; //redirect к get запросу
        }
    }

    /**
     * Удаление задачи
     * POST
     */
    @PostMapping("/user/folder/issue/delete")
    public String deleteIssue(@ModelAttribute("folderId") Long folderId,
                              @ModelAttribute("issueId") Long issueId) {
        issueRepoService.deleteById(issueId);
        return "redirect:/user/folders/" + folderId;
    }

    /**
     *
     */
    @PostMapping("/user/folder/issue/changeState")
    public String changeState(@ModelAttribute("folderId") Long folderId,
                              @ModelAttribute("issueId") Long issueId) {
        IssueEntity issueEntity = issueRepoService.findById(issueId);
        if (issueEntity.isDone()) {
            issueEntity.setDone(false);
        } else {
            issueEntity.setDone(true);
        }
        issueRepoService.save(issueEntity);
        return "redirect:/user/folders/" + folderId;
    }
}
