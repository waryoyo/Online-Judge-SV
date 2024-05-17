package com.example.megumin.submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/judge")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public String postSubmission(@RequestBody Submission submission) {
        submissionService.createSubmission(submission);
        return submissionService.runSubmission(submission);

    }
}
