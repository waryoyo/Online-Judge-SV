package com.example.megumin.problem;

import com.example.megumin.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<ProblemSimpleProjection> findBy();

}