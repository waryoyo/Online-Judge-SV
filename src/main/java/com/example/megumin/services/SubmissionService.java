package com.example.megumin.services;


import com.example.megumin.DTO.ProblemDTO;
import com.example.megumin.DTO.SourceCodeDTO;
import com.example.megumin.DTO.TestCaseDTO;
import com.example.megumin.models.Problem;
import com.example.megumin.models.Submission;
import com.example.megumin.repositories.SubmissionRepository;
import com.example.megumin.models.SubmissionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository){
        this.submissionRepository = submissionRepository;
    }

    public Submission createSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }
    public Submission getSubmissionById(long id) {
        return submissionRepository.findById(id).orElse(null);
    }
    public ProblemDTO getProblemDTO(Submission submission){
        Problem problem = submission.getProblem();

        ProblemDTO problemDTO = new ProblemDTO();
        SourceCodeDTO sourceCodeDTO = new SourceCodeDTO();

        sourceCodeDTO.setContent(submission.getSourceCode().getContent());
        sourceCodeDTO.setLanguage(submission.getSourceCode().getLanguage());
        problemDTO.setSource_code(sourceCodeDTO);
        problemDTO.setSubmission_id(submission.getId());
        problemDTO.setTime_limit(problem.getTimeLimit());
        problemDTO.setMemory_limit(problem.getMemoryLimit());

        List<TestCaseDTO> testCaseDTOs = problem.getTestCases().stream()
                .map(tc -> {
                    TestCaseDTO dto = new TestCaseDTO();
                    dto.setInput(tc.getInput());
                    dto.setOutput(tc.getOutput());
                    return dto;
                })
                .toList();

        problemDTO.setTest_cases(testCaseDTOs);
        return problemDTO;

    }
    @Async
    public void runSubmission(Submission submission) {
        submission.setStatus(SubmissionStatus.RUNNING);
        Submission savedSubmission = submissionRepository.save(submission);

        try(var client = HttpClient.newHttpClient()){
            ProblemDTO problemDTO = getProblemDTO(savedSubmission);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(problemDTO);

            var request = HttpRequest.newBuilder(
                            URI.create("http://localhost:5010/judge"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody,  StandardCharsets.UTF_8))
                    .build();
            CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            responseFuture.thenApply(HttpResponse::body)
                    .thenAccept(responseBody -> {
                        try {
                            // Parse the response body to an object
                            Map<String, String> responseMap = objectMapper.readValue(responseBody, Map.class);
                            String status = responseMap.get("status");

                            if(status.equals("AC"))
                                submission.setStatus(SubmissionStatus.SUCCESS);
                            else
                                submission.setStatus(SubmissionStatus.ERROR);

                            submissionRepository.save(submission);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .exceptionally(e -> {
                        e.printStackTrace();
                        return null;
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
