package com.example.megumin.user;

import com.example.megumin.submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
