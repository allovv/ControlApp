package com.springapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectsController {

    @GetMapping("/projects")
    public String projects(Model model){
        return "projects";
    }

    //TODO: добавить выход из системы в шаблон
}
