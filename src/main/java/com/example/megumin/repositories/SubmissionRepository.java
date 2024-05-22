package com.example.megumin.repositories;

import com.example.megumin.models.Submission;
import com.example.megumin.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser(User user);

}
