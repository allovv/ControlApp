package com.springapp.controllers.common;

import com.springapp.entities.Roles;
import com.springapp.entities.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.springapp.controllers.ControllersTools.getUserSecurity;

@Controller
public class RedirectController {

    public static final String SUCCESS_AUTH_URL = "/redirect";

    @GetMapping(SUCCESS_AUTH_URL)
    public String redirect(Model model) {
        //TODO: перенаправление (потом изменить)
        UserEntity userEntity = getUserSecurity();

        for (Roles role : userEntity.getRoles()) {
            if (role == Roles.ADMIN) {
                return "redirect:/admin";
            }
        }

        return "redirect:/user";
    }
}
