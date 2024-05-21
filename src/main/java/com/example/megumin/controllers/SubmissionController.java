package com.example.megumin.controllers;

import com.example.megumin.models.Submission;
import com.example.megumin.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/submission")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public Submission postSubmission(@ModelAttribute("submission") Submission submission) {
        Submission submission1 = submissionService.createSubmission(submission);
        submissionService.runSubmission(submission);
        return submission1;
    }
}
