package com.springapp.controllers.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.springapp.controllers.ControllersTools.isAuthenticated;
import static com.springapp.controllers.common.RedirectController.SUCCESS_AUTH_URL;

@Controller
public class LoginController {

    /**
     * Обработка страницы login
     * GET
     */
    @GetMapping("/login")
    public String login(Model model) {

        if (isAuthenticated()) { //TODO: перенаправление (изменить)
            return "redirect:" + SUCCESS_AUTH_URL;
        }

        return "login";
    }
}
