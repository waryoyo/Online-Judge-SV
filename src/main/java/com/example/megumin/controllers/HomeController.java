package com.example.megumin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/home";
    }
    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("pageContent", "home");
        return "layout";
    }


}
