package com.example.megumin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problem")
public class ProblemController {
    @GetMapping("/**")
    public String getProblemsListPage() {
        return "redirect:/problem/list";
    }
    @GetMapping("/list")
    public String getProblemsListPage(Model model) {
        model.addAttribute("pageContent", "problem/problemsList");
        return "layout";
    }
    @GetMapping("/{id}")
    public String getProblemPage(Model model) {
        model.addAttribute("pageContent", "problem/problemsList");
        return "layout";
    }
}
