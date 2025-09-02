/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.service;

import com.example.authorverse.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    List<Book> getAllBooksWithAuthors();
    List<Book> getBooksByAuthorId(Long authorId);

}
