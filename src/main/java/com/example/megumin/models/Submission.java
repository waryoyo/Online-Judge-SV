package com.example.megumin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SourceCode sourceCode;
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    @JsonBackReference
    private Problem problem;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private Double runTime;
    private Double memoryUsed;

    public Long getId() {
        return id;
    }

    public SourceCode getSourceCode() {
        return sourceCode;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }
    public Problem getProblem() {
        return problem;
    }
    public void setRunTime(Double runTime) {
        this.runTime = runTime;
    }
    public void setMemoryUsed(Double memoryUsed) {
        this.memoryUsed = memoryUsed;
    }
    public Double getRunTime() {
        return this.runTime;
    }
    public Double getMemoryUsed() {
        return this.memoryUsed;
    }
}