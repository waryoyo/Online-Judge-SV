package com.example.megumin.services;

import com.example.megumin.models.Problem;
import com.example.megumin.models.User;
import com.example.megumin.payloads.ProblemSimpleProjection;
import com.example.megumin.repositories.ProblemRepository;
import com.example.megumin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemService {
    @Autowired
    private UserRepository userRepository;
    private final ProblemRepository problemRepository;

    @Autowired
    public ProblemService(ProblemRepository problemRepository){
        this.problemRepository = problemRepository;
    }

    public List<Problem> getAllProblems() {
        return new ArrayList<>(problemRepository.findAll());
    }
    public List<ProblemSimpleProjection> getAllProblemsList() {
        return problemRepository.findBy();
    }


    public Problem getProblemById(Long id) {
        return problemRepository.findById(id).orElse(null);
    }

    public Problem createProblem(Problem problem) {
        problem.setDate(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            problem.setUser(userRepository.findByEmail(authentication.getName()).orElse(null));
        } else {
            problem.setUser(null);
        }


        return problemRepository.save(problem);
    }
    public Problem updateProblem(Long id, Problem problemDetails) {
        Problem problem = problemRepository.findById(id).orElse(null);
        if (problem != null)
            return problemRepository.save(problemDetails);

        return null;
    }

    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }
}