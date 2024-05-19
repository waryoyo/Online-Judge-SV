package com.example.megumin.apiControllers;

import com.example.megumin.models.Problem;
import com.example.megumin.services.ProblemService;
import com.example.megumin.DTO.ProblemSimpleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
public class ProblemAPIController {
    private final ProblemService problemService;

    @Autowired
    public ProblemAPIController(ProblemService problemService) {
        this.problemService = problemService;
    }
    @GetMapping
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }
    @GetMapping("/list")
    public List<ProblemSimpleProjection> getAllProblemsList() {
        return problemService.getAllProblemsList();
    }
    @PostMapping
    public Problem postProblem(@RequestBody Problem problem) {
        System.out.println("hello");
        return problemService.createProblem(problem);
    }

}