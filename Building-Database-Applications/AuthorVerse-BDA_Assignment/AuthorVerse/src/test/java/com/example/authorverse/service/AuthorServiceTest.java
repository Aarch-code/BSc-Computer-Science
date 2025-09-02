/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.service;

import com.example.authorverse.model.Author;
import com.example.authorverse.repository.AuthorRepository;
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
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(
                new Author("Author 1", "author1@example.com"),
                new Author("Author 2", "author2@example.com")
        ));

        List<Author> authors = authorService.getAllAuthors();
        assertEquals(2, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        Author author = new Author("Author 1", "author1@example.com");
        author.setId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author found = authorService.getAuthorById(1L);
        assertEquals(author.getName(), found.getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveAuthor() {
        Author author = new Author("New Author", "new@example.com");
        when(authorRepository.save(author)).thenReturn(author);

        Author saved = authorService.saveAuthor(author);
        assertNotNull(saved);
        verify(authorRepository, times(1)).save(author);
    }

    

}