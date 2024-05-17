package com.example.megumin.submission;

import com.example.megumin.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
