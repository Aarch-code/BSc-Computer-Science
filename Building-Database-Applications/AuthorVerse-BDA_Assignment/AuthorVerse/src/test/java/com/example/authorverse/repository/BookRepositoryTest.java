/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.repository;

import com.example.authorverse.model.Author;
import com.example.authorverse.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        // Clear data before each test
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        // Test empty repository first
        List<Book> emptyBooks = bookRepository.findAll();
        assertTrue(emptyBooks.isEmpty());

        // Add test data
        Author author = new Author("Test Author", "test@example.com");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book("Test Book", "1234567890", 2023, savedAuthor);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    public void testFindById() {
        Author author = new Author("Test Author", "test@example.com");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book("Test Book", "1234567890", 2023, savedAuthor);
        Book savedBook = bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    @Test
    public void testFindAllWithAuthors() {
        Author author = new Author("Test Author", "test@example.com");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book("Test Book", "1234567890", 2023, savedAuthor);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllWithAuthors();
        assertFalse(books.isEmpty());
        assertNotNull(books.get(0).getAuthor());
    }

    @Test
    public void testFindByAuthorId() {
        Author author = new Author("Test Author", "test@example.com");
        Author savedAuthor = authorRepository.save(author);

        Book book1 = new Book("Test Book 1", "1234567890", 2023, savedAuthor);
        Book book2 = new Book("Test Book 2", "0987654321", 2023, savedAuthor);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findByAuthorId(savedAuthor.getId());
        assertFalse(books.isEmpty());
        assertEquals(2, books.size());
    }
    
    @Test
    public void testBookMissingFields() {
        Author author = authorRepository.save(new Author("Author", "a@mail.com"));
        Book invalidBook = new Book(null, null, 2023, author); // Missing title and ISBN
        assertThrows(Exception.class, () -> {
            bookRepository.saveAndFlush(invalidBook);
        });
    }

    @Test
    public void testDuplicateIsbnThrowsException() {
        Author author = authorRepository.save(new Author("Duplicate Author", "dup@example.com"));
    
        Book book1 = new Book("Book One", "UNIQUISBN123", 2022, author);
        Book book2 = new Book("Book Two", "UNIQUISBN123", 2024, author); // Same ISBN
    
        bookRepository.save(book1);
        assertThrows(Exception.class, () -> {
            bookRepository.saveAndFlush(book2);
        });
    }
    

    @Test
    public void testCreateBookWithoutAuthorShouldThrowException() {
        Book invalidBook = new Book();
        invalidBook.setTitle("No Author");
        invalidBook.setIsbn("ISBN123");
        invalidBook.setPublicationYear(2024);
        // author == null -> violates the FK @JoinColumn(nullable=false)
    
        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.saveAndFlush(invalidBook);
        });
    }

    @Test
    public void testUpdateBookWithNullTitleShouldThrowException() {
        // 1) Save a valid book
        Author author = authorRepository.save(new Author("Author A", "a@example.com"));
        Book book = new Book("Original Title", "ISBN-XYZ", 2023, author);
        Book savedBook = bookRepository.save(book);
    
        // 2) Nuke the title
        savedBook.setTitle(null);
    
        // 3) Expect exception on flush
        assertThrows(DataIntegrityViolationException.class, () -> {
            bookRepository.saveAndFlush(savedBook);
        });
    }

    
}
