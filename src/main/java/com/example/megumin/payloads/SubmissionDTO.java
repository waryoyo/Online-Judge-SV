package com.example.megumin.payloads;

import com.example.megumin.models.Problem;
import com.example.megumin.models.SubmissionStatus;

public class SubmissionDTO {
    private Problem problem;
    private SubmissionStatus status;
    public SubmissionDTO() {}

    public SubmissionDTO(Problem problem, SubmissionStatus status) {
        this.problem = problem;
        this.status = status;
    }
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }
}
