package com.example.megumin.submission;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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
    @Async
    public void runSubmission(Submission submission) {
        submission.setStatus(SubmissionStatus.RUNNING);
        submissionRepository.save(submission);

        try(var client = HttpClient.newHttpClient()){
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(submission.getSourceCode());

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

                            if(status.equals("SUCCESS"))
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
