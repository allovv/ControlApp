package com.springapp.controllers;

import com.springapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //-------------------------------------------------------------
    @Autowired
    private UserRepository userRepository;
    //-------------------------------------------------------------

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "home"; //вызываем шаблон по названию
    }

}
