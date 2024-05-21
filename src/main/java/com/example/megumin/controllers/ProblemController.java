package com.example.megumin.controllers;

import com.example.megumin.models.Problem;
import com.example.megumin.services.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemService problemService;
    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }


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
        Problem problem = problemService.getProblemById(id);
        model.addAttribute("problem", problem);
        model.addAttribute("pageContent", "problem/problemPage");
        return "layout";
    }
    @GetMapping("/addProblem")
    public String addProblemPage(Model model) {
        model.addAttribute("pageContent", "problem/createProblem");
        return "layout";
    }
    @PostMapping("/addProblem")
    public String postProblem(@ModelAttribute("problem") Problem problem, Model model) {
        problemService.createProblem(problem);
        return "redirect:/problem/list";
    }
}
