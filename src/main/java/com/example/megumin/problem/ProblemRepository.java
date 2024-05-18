package com.example.megumin.problem;

import com.example.megumin.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
