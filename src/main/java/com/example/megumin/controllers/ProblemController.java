package com.example.megumin.controllers;

import com.example.megumin.models.Problem;
import com.example.megumin.repositories.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problem")
public class ProblemController {
    @Autowired
    private ProblemRepository problemRepository;

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
    public String getProblemPage(@PathVariable Long id, Model model) {
        Problem problem = problemRepository.findById(id).orElseThrow(null);
        model.addAttribute("problem", problem);
        model.addAttribute("pageContent", "problem/problemPage");
//        return "problem-details";
        return "layout";
    }
}
