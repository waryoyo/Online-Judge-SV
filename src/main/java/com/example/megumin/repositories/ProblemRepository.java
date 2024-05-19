package com.example.megumin.repositories;

import com.example.megumin.models.Problem;
import com.example.megumin.payloads.ProblemSimpleProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<ProblemSimpleProjection> findBy();

}