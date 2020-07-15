package com.springapp.controllers.customers;

import com.springapp.entities.IssueEntity;
import com.springapp.services.IssueRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.springapp.controllers.ControllersTools.getFieldErrors;

@Controller
public class UserIssueController {
    //-------------------------------------------------------------
    @Autowired
    IssueRepoService issueRepoService;

    //-------------------------------------------------------------
    /**
     * Добавление задачи
     * POST
     */
    @PostMapping("/user/folder/issue")
    public String addIssue(@ModelAttribute("newIssue") @Valid IssueEntity newIssue, BindingResult bindingResult,
                           @ModelAttribute("currentFolderId") Long currentFolderId,
                           HttpSession httpSession,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = getFieldErrors(bindingResult);
            for (FieldError fieldError : fieldErrors) {
                //redirect attr
                redirectAttributes.addFlashAttribute(fieldError.getField() + "AddIssueError", fieldError.getDefaultMessage());
            }

        } else {
            if (!issueRepoService.addIssue(newIssue)) {
                //redirect attr
                redirectAttributes.addFlashAttribute("existAddIssueError", "Ошибка при создании задачи!");
            }

        }
        //перенаправление к /user/folders/{folderId}
        return "redirect:/user/folders/" + currentFolderId;
    }

    /**
     * Редактирование задачи
     * POST
     */
    @PostMapping("/user/folder/issue/edit")
    public String saveEditIssue(@ModelAttribute("newIssue") @Valid IssueEntity editedIssue, BindingResult bindingResult,
                                @ModelAttribute("currentFolderId") Long currentFolderId,
                                @ModelAttribute("issueId") Long issueId,
                                HttpSession httpSession,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError: getFieldErrors(bindingResult)) {
                redirectAttributes.addFlashAttribute(fieldError.getField() + "EditIssueError", fieldError.getDefaultMessage());
                return "redirect:/user/folders/" + currentFolderId;
            }
        }

        issueRepoService.editIssueById(editedIssue, issueId);
        return "redirect:/user/folders/" + currentFolderId;
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
     * Изменение состояния задачи
     * POST
     */
    @PostMapping("/user/folder/issue/changeState")
    public String changeState(@ModelAttribute("folderId") Long folderId,
                              @ModelAttribute("issueId") Long issueId) {
        IssueEntity issueEntity = issueRepoService.findById(issueId);

        issueEntity.setDone(!issueEntity.isDone());
        issueRepoService.save(issueEntity);
        return "redirect:/user/folders/" + folderId;
    }

    /**
     * Копирование задачи
     * POST
     */
    @PostMapping("/user/{folderId}/issue/duplicate")
    public String duplicateIssue(@PathVariable("folderId") Long folderId,
                                 @ModelAttribute @Valid IssueEntity duplicateIssue, BindingResult bindingResult,
                                 HttpSession httpSession,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("DuplicateIssueError", "Не удалось дублировать задачу");
        } else {
            if (!issueRepoService.addIssue(duplicateIssue)) {
                //redirect attr
                redirectAttributes.addFlashAttribute("DuplicateIssueError", "Ошибка при дублировании задачи задачи!");
            }
        }
        return "redirect:/user/folders/" + folderId;
    }

}
