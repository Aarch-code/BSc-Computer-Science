/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.service;

import com.example.authorverse.model.Book;
import com.example.authorverse.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    
    public Book saveBook(Book book) {
        // Check if a book with the same ISBN already exists
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            // If it exists, throw an exception or return null to indicate the issue
            throw new IllegalArgumentException("This ISBN already exists in the system.");
        }
        
        // If no existing book with that ISBN, proceed with saving the new book
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setAuthor(book.getAuthor());
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAllBooksWithAuthors() {
        return bookRepository.findAllWithAuthors();
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
    
}