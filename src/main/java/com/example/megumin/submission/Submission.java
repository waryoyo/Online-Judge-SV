package com.example.megumin.submission;

import com.example.megumin.codeRunner.SourceCode;
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
    private String output;

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

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
