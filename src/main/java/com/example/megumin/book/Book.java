package com.example.megumin.book;
import jakarta.persistence.*;

import org.springframework.lang.NonNull;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }

}
