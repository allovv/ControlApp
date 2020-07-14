package com.springapp.controllers.customers;

import com.springapp.entities.FolderEntity;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.springapp.controllers.ControllersTools.getFieldErrors;

@Controller
public class UserFolderController {
    //-------------------------------------------------------------
    @Autowired
    private FolderRepoService folderRepoService;
    @Autowired
    private IssueRepoService issueRepoService;

    //-------------------------------------------------------------
    /**
     * Вывод информации по конкретной папке
     * GET
     */
    @GetMapping("/user/folders/{folderId}")
    public String getFolder(@AuthenticationPrincipal UserEntity userEntity,
                            @PathVariable("folderId") Long folderId,
                            Map<String, Object> model){
        //Add attributes
        model.put("userEntity", userEntity);
        model.put("folders", folderRepoService.findAllByCreatorId(userEntity.getId()));
        model.put("currentFolder", folderRepoService.findById(folderId));
        model.put("issues", issueRepoService.findAllByFolderId(folderId));

        HashSet<String> tags = new HashSet<>();
        for (IssueEntity issue : issueRepoService.findAllByFolderId(folderId)) {
            for (String tag : issue.getTags()) {
                tags.add(tag);
            }
        }
        model.put("tagsToSorting", tags);

        return "user";
    }

    /**
     * Вывод информации по конкретной папке с сортировкой по тегу
     * GET
     */
    @GetMapping("/user/folders/{folderId}/sort/{tag}")
    public String sortFolderByTag(@PathVariable("folderId") Long folderId,
                                  @AuthenticationPrincipal UserEntity userEntity,
                                  @PathVariable("tag") String tagToSort,
                                  Map<String, Object> model) {

        //Add attributes
        model.put("userEntity", userEntity);
        model.put("folders", folderRepoService.findAllByCreatorId(userEntity.getId()));
        model.put("currentFolder", folderRepoService.findById(folderId));

        Set<IssueEntity> issues = new HashSet<>();
        for (IssueEntity issueEntity : issueRepoService.findAllByFolderId(folderId)) {
            if (issueEntity.getTags().contains(tagToSort)) {
                issues.add(issueEntity);
            }
        }
        model.put("issues", issues);

        HashSet<String> tags = new HashSet<>();
        for (IssueEntity issue : issueRepoService.findAllByFolderId(folderId)) {
            for (String tag : issue.getTags()) {
                tags.add(tag);
            }
        }
        model.put("tagsToSorting", tags);

        model.put("currentTag", tagToSort);

        return "user";
    }

    //-------------------------------------------------------------
    /**
     * Добавление папки
     * POST
     */
    @PostMapping("/user/folders")
    public String addFolder(@ModelAttribute @Valid FolderEntity folderEntity, BindingResult bindingResult,
                            HttpSession httpSession,
                            RedirectAttributes redirectAttributes,
                            Model model){
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = getFieldErrors(bindingResult);
            for (FieldError fieldError : fieldErrors) {
                //redirect
                redirectAttributes.addFlashAttribute(fieldError.getField() + "AddFolderError", fieldError.getDefaultMessage());
            }

            return "redirect:/user";
        } else {
            if (!folderRepoService.addFolder(folderEntity)) {
                //redirect
                redirectAttributes.addFlashAttribute("existAddFolderError", "Идентификатор пользователя не указан!!");

                return "redirect:/user";
            }

            //перенаправление после успешного создания папки к /user/folders/{folderId}
            return "redirect:/user/folders/" + folderEntity.getId();
        }
    }

    /**
     * Удаление папки
     * POST
     */
    @PostMapping("/user/folders/delete")
    public String deleteFolder(@ModelAttribute("folderId") Long folderId, Model model){
        folderRepoService.deleteById(folderId);

        return "redirect:/user";
    }

    /**
     * Редактирование папки
     * POST
     */
    @PostMapping("/user/folders/edit")
    public String editFolder(@AuthenticationPrincipal UserEntity userEntity,
                             @ModelAttribute("folderId")  Long folderId,
                             @ModelAttribute("name") String name,
                             HttpSession httpSession,
                             RedirectAttributes redirectAttributes,
                             Model model){
        if (name == null || name.isEmpty()) {
            //redirect (flash аттрибут)
            redirectAttributes.addFlashAttribute("editNameFolderError", "Поле не должно быть пустым");

            return "redirect:/user/folders/" + folderId;
        }

        if (folderRepoService.isExist(name, userEntity.getId())) {
            //redirect (flash аттрибут)
            redirectAttributes.addFlashAttribute("editExistNameFolderError", "Папка с таким названием уже существует");

            return "redirect:/user/folders/" + folderId;
        }

        folderRepoService.editFolderById(name, folderId);

        return "redirect:/user/folders/" + folderId;
    }
}
