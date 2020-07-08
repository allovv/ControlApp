package com.springapp.controllers.common;

import com.springapp.entities.UserEntity;
import com.springapp.services.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    //-------------------------------------------------------------
    @Autowired
    private UserRepoService userRepoService;

    //-------------------------------------------------------------

    /**
     * Получение страницы регистрации
     * GET
     */
    @GetMapping("/registration")
    public String registration(UserEntity userEntity, Model model) {
        return "registration";
    }

    /**
     * Регистрирование пользователя
     * POST
     */
    @PostMapping("/registration")
    public String addUser(@RequestParam String confirmPassword,
                          @ModelAttribute @Valid UserEntity userEntity,
                          BindingResult bindingResult,
                          Model model) {
        if (confirmPassword.isEmpty()) {
            model.addAttribute("confirmPasswordError", "Подтверждающий пароль не должен быть пустым.");
        }
        if (userEntity.getPassword() != null && !userEntity.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordsEqualsError", "Пароли не совпадают!");
        }
        if (bindingResult.hasErrors() || confirmPassword.isEmpty() || !userEntity.getPassword().equals(confirmPassword)) {
            return "registration";
        } else {
            if (userRepoService.registerUser(userEntity)) {

                return "redirect:/login";
            } else {
                model.addAttribute("userExistError", "Пользователь с таким email уже зарегистрирован!");

                return "registration";
            }
        }

    }

}
