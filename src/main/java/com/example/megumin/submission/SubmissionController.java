package com.example.megumin.submission;

import com.example.megumin.codeRunner.CodeRunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final CodeRunnerService codeRunnerService;

    @Autowired
    public SubmissionController(SubmissionService submissionService, CodeRunnerService codeRunnerService) {
        this.submissionService = submissionService;
        this.codeRunnerService = codeRunnerService;
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
