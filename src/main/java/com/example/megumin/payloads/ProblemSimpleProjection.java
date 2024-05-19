package com.example.megumin.payloads;

import com.example.megumin.models.ProblemDifficulty;

public interface ProblemSimpleProjection {
    Long getId();
    String getTitle();
    ProblemDifficulty getDifficulty();

}
