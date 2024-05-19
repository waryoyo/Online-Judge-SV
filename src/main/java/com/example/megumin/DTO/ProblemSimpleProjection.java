package com.example.megumin.DTO;

import com.example.megumin.models.ProblemDifficulty;

public interface ProblemSimpleProjection {
    Long getId();
    String getTitle();
    ProblemDifficulty getDifficulty();

}
