/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    
    @Column(unique = true ,nullable = false)
    private String isbn;
    
    @Column(nullable = false)
    private int publicationYear;
    
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Constructors, getters, and setters
    public Book() {}

    public Book(String title, String isbn, int publicationYear, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}