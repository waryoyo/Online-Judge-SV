package com.example.megumin.problem;

import com.example.megumin.submission.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {
    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
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