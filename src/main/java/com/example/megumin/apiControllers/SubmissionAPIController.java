package com.example.megumin.apiControllers;

import com.example.megumin.models.Submission;
import com.example.megumin.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/submission")
public class SubmissionAPIController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionAPIController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }
    @GetMapping()
    public List<Submission> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public Submission getSubmissionById(@PathVariable Long id) {
        return submissionService.getSubmissionById(id);
    }

    @PostMapping
    public Submission postSubmission(@RequestBody Submission submission) {
        Submission submission1 = submissionService.createSubmission(submission);
        submissionService.runSubmission(submission);
        return submission1;
    }
}
