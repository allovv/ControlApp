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
    @PostMapping("/user/folder/{folderId}/issue")
    public String addIssue(@ModelAttribute @Valid IssueEntity issueEntity,
                           @PathVariable("folderId") Long folderId,
                           BindingResult bindingResult,
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
                redirectAttributes.addFlashAttribute("existAddIssueError", "Задача с таким названием уже создана!");

                return "redirect:/user/folders/" + folderId;
            }

            //перенаправление после успешного создания задачи к /user/folders/{folderId}
        }
        return "redirect:/user/folders/" + folderId;
    }
}
