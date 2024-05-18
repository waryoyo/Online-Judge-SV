package com.example.megumin.problem;

import com.example.megumin.submission.Submission;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String statement;
    @Column(nullable = false)
    private String language;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProblemDifficulty difficulty;
    @Column(nullable = false)
    private long timeLimit;
    @Column(nullable = false)
    private long memoryLimit;
    @Column(nullable = false)
    private long timesSolved;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Submission> submissions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ProblemDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(ProblemDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public long getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public long getTimesSolved() {
        return timesSolved;
    }

    public void setTimesSolved(long timesSolved) {
        this.timesSolved = timesSolved;
    }

    public Long getId() {
        return id;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }
}
