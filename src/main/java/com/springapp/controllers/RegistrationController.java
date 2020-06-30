package com.springapp.controllers;

import com.springapp.entities.Roles;
import com.springapp.entities.UserEntity;
import com.springapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController {
    //-------------------------------------------------------------
    @Autowired
    private UserRepository userRepository;
    //-------------------------------------------------------------

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute UserEntity userEntity, Map<String, Object> model) {
        //проверка при регистрации
        UserEntity userEntityDB = userRepository.findByEmail(userEntity.getEmail());
        if (userEntityDB != null) {
            model.put("errorMessage", "Пользователь с таким email уже зарегистрирован!");
            return "registration";
        }

        //установка ролей TODO: по умолчанию все просто USER
        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.USER);
        userEntity.setRoles(roles);

        //сохранение пользователя
        userRepository.save(userEntity);

        return "redirect:/login";
    }

}
