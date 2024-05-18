package com.example.megumin.submission;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final BlockingQueue<Submission> submissionQueue = new LinkedBlockingQueue<>();

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository){
        this.submissionRepository = submissionRepository;
    }
    @PostConstruct
    public void init() {
        for (int i = 0; i < 2; i++) {
            executorService.submit(this::processSubmissions);
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private void processSubmissions() {
        while (true) {
            try {
                Submission submission = submissionQueue.take();
                submission.setStatus(SubmissionStatus.RUNNING);
                submissionRepository.save(submission);

                String result = runSubmission(submission);
                if(result == null)
                    submission.setStatus(SubmissionStatus.ERROR);
                else{
                    submission.setOutput(result);
                    submission.setStatus(SubmissionStatus.SUCCESS);
                }

                submissionRepository.save(submission);
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Submission createSubmission(Submission submission) {
        var submissionSaved = submissionRepository.save(submission);
        submissionQueue.add(submissionSaved);
        return submissionSaved;
    }
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }
    public Submission getSubmissionById(long id) {
        return submissionRepository.findById(id).orElse(null);
    }

    public String runSubmission(Submission submission) {
        try {
            String[] commands = new String[2];
            commands[0] = "g++ main.cpp -o main";
            commands[1] = ".\\main";

            FileWriter fileWriter = new FileWriter("main.cpp");
            fileWriter.write(submission.getSourceCode().getContent());
            fileWriter.close();

            DefaultExecutor executor = DefaultExecutor.builder().get();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            executor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream));

            executor.execute(CommandLine.parse(commands[0]));
            executor.execute(CommandLine.parse(commands[1]));

            return byteArrayOutputStream.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
