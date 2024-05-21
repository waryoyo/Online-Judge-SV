package com.example.megumin.services;


import com.example.megumin.payloads.ProblemDTO;
import com.example.megumin.payloads.SourceCodeDTO;
import com.example.megumin.payloads.TestCaseDTO;
import com.example.megumin.models.Problem;
import com.example.megumin.models.Submission;
import com.example.megumin.repositories.SubmissionRepository;
import com.example.megumin.models.SubmissionStatus;
import com.fasterxml.jackson.databind.JsonNode;
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
            // Shall it be in the lambda?
            CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            responseFuture.thenApply(HttpResponse::body)
                    .thenAccept(responseBody -> {
                        try {
                            boolean systemError = false;
                            boolean compilationError = false;
                            boolean runTimeError = false;
                            boolean wrongAnswer = false;
                            boolean timeLimitExceeded = false;
                            boolean memoryLimitExceeded = false;
                            double maxTime = 0;
                            double maxMemory = 0;
                            // Parse the response body to an object
                            JsonNode jsonNode = objectMapper.readTree(responseBody);
                            String submissionID = jsonNode.get("submission_id").asText();
                            JsonNode verdicts = jsonNode.get("test_verdicts");
                            for (JsonNode node : verdicts) {
                                String status = node.get("verdict").asText();
                                if (status.equals("WA")) wrongAnswer = true;
                                if (status.equals("TLE")) timeLimitExceeded = true;
                                if (status.equals("MLE")) memoryLimitExceeded = true;
                                if (status.equals("ERUN")) runTimeError = true;
                                if (status.equals("ECOM")) systemError = true;
                                if (status.equals("ESYS")) systemError = true;
                                double runTime = node.get("run_time").asDouble();
                                double memoryUsed = node.get("memory_used").asDouble();
                                maxTime = Math.max(runTime, maxTime);
                                maxMemory = Math.max(memoryUsed, maxMemory);
                            }
                            if (systemError) submission.setStatus(SubmissionStatus.ESYS);
                            else if (compilationError) submission.setStatus(SubmissionStatus.ECOM);
                            else if (runTimeError) submission.setStatus(SubmissionStatus.ERUN);
                            else if (wrongAnswer) submission.setStatus(SubmissionStatus.WA);
                            else if (timeLimitExceeded) submission.setStatus(SubmissionStatus.TLE);
                            else if (memoryLimitExceeded) submission.setStatus(SubmissionStatus.MLE);
                            else submission.setStatus(SubmissionStatus.AC);
                            submission.setRunTime(maxTime);
                            submission.setMemoryUsed(maxMemory);
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
