package com.example.megumin.submission;

import com.example.megumin.book.Book;
import com.example.megumin.book.BookRepository;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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
    public String runSubmission(Submission submission) {
        try {
            String[] commands = new String[3];
            commands[0] = "g++ main.cpp -o main";
            commands[1] = ".\\main";

            FileWriter fileWriter = new FileWriter("main.cpp");
            fileWriter.write(submission.getContent());
            fileWriter.close();

            DefaultExecutor executor = DefaultExecutor.builder().get();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            executor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream));

            executor.execute(CommandLine.parse(commands[0]));
            executor.execute(CommandLine.parse(commands[1]));

            return byteArrayOutputStream.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
