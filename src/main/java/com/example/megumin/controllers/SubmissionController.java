package com.example.megumin.controllers;

import com.example.megumin.models.Submission;
import com.example.megumin.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }
    @GetMapping("/list")
    public String postSubmission(Model model) {
        model.addAttribute("pageContent", "submissionslist");
        return "layout";
    }
    @PostMapping
    public Submission postSubmission(@ModelAttribute("submission") Submission submission) {
        Submission submission1 = submissionService.createSubmission(submission);
        submissionService.runSubmission(submission);
        return submission1;
    }
}
