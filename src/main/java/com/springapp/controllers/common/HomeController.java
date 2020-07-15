package com.springapp.controllers.common;

import com.springapp.services.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.springapp.controllers.ControllersTools.isAuthenticated;
import static com.springapp.controllers.common.RedirectController.SUCCESS_AUTH_URL;

@Controller
public class HomeController {
    //-------------------------------------------------------------
    @Autowired
    UserRepoService userRepoService;

    //-------------------------------------------------------------
    /**
     * Обработка страницы HOME
     * GET
     */
    @GetMapping("/")
    public String home(Model model) {

        if (isAuthenticated()) {
            return "redirect:" + SUCCESS_AUTH_URL;
        }

        //Add attributes
        model.addAttribute("users", userRepoService.findAll());

        return "home";
    }

}
