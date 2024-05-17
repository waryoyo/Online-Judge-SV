package com.example.megumin.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
}
