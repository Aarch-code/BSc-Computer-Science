/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.repository;

import com.example.authorverse.model.Author;
import com.example.authorverse.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        // Test empty repository
        List<Author> emptyAuthors = authorRepository.findAll();
        assertTrue(emptyAuthors.isEmpty());

        // Add test data
        Author author = new Author("Test Author", "test@example.com");
        authorRepository.save(author);

        List<Author> authors = authorRepository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(1, authors.size());
    }

    @Test
    public void testFindById() {
        Author author = new Author("Test Author", "test@example.com");
        Author savedAuthor = authorRepository.save(author);

        Author foundAuthor = authorRepository.findById(savedAuthor.getId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals("Test Author", foundAuthor.getName());
    }

    @Test
    public void testFindAllWithBooks() {
        // Create and save author with books
        Author author = new Author("Test Author", "test@example.com");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book("Test Book", "1234567890", 2023, savedAuthor);
        savedAuthor.addBook(book);  // Use the convenience method
        bookRepository.save(book);

        // Flush and clear to ensure the query hits the database
        entityManager.flush();
        entityManager.clear();

        // Test the custom query
        List<Author> authorsWithBooks = authorRepository.findAllWithBooks();
        
        assertFalse(authorsWithBooks.isEmpty());
        assertEquals(1, authorsWithBooks.size());
        
        Author retrievedAuthor = authorsWithBooks.get(0);
        assertNotNull(retrievedAuthor.getBooks(), "Books collection should not be null");
        assertFalse(retrievedAuthor.getBooks().isEmpty(), "Books collection should not be empty");
        assertEquals(1, retrievedAuthor.getBooks().size());
    }

    @Test
    public void testDataIntegrityViolation() {
        // Test that saving an author without required fields throws exception
        Author invalidAuthor = new Author();
        invalidAuthor.setName("Name Only");
        
        assertThrows(DataIntegrityViolationException.class, () -> {
            authorRepository.saveAndFlush(invalidAuthor);
        });
    }

    @Test
    public void testUpdateAuthorWithNullEmailShouldThrowException() {
        // 1) Save a valid author
        Author author = new Author("Valid Author", "valid@example.com");
        Author saved = authorRepository.save(author);
    
        // 2) Nuke the email (violates @Column(nullable = false))
        saved.setEmail(null);
    
        // 3) Assert that saveAndFlush blows up
        assertThrows(DataIntegrityViolationException.class, () -> {
            authorRepository.saveAndFlush(saved);
        });
    }
    
   
}




