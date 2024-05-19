package com.example.megumin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class ProblemController {
    @RequestMapping("/problems")
    public String getProblemsListPage(Model model) {
        model.addAttribute("pageContent", "problem/problemsList");
        return "layout";
    }
    @RequestMapping("/problem/{id}")
    public String getProblemPage(Model model) {
        model.addAttribute("pageContent", "problem/problemsList");
        return "layout";
    }
}
