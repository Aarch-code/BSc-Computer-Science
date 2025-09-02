/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.service;

import com.example.authorverse.model.Author;
import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author saveAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
    List<Author> getAllAuthorsWithBooks();
}