/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.service;

import com.example.authorverse.model.Author;
import com.example.authorverse.model.Book;
import com.example.authorverse.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() {
        Author author = new Author("Author 1", "author1@example.com");
        when(bookRepository.findAllWithAuthors()).thenReturn(Arrays.asList(
                new Book("Book 1", "ISBN1", 2020, author),
                new Book("Book 2", "ISBN2", 2021, author)
        ));

        List<Book> books = bookService.getAllBooksWithAuthors();
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAllWithAuthors();
    }

    @Test
    public void testGetBookById() {
        Author author = new Author("Author 1", "author1@example.com");
        Book book = new Book("Book 1", "ISBN1", 2020, author);
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book found = bookService.getBookById(1L);
        assertEquals(book.getTitle(), found.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveBook() {
        Author author = new Author("Author 1", "author1@example.com");
        Book book = new Book("New Book", "NEWISBN", 2022, author);
        when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.saveBook(book);
        assertNotNull(saved);
        verify(bookRepository, times(1)).save(book);
    }   
}