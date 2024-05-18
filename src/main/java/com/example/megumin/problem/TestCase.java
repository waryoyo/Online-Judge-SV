package com.example.megumin.problem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class TestCase {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String input;
    private String output;
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    @JsonBackReference
    private Problem problem;

    public long getId() {
        return id;
    }
    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    public Problem getProblem() {
        return problem;
    }
}
