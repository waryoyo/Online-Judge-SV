package com.example.megumin.problem;

import com.example.megumin.problem.Problem;
import com.example.megumin.submission.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemService {
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