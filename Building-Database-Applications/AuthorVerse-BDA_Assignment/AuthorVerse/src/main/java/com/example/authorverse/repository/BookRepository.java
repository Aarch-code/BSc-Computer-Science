/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.repository;

import com.example.authorverse.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN FETCH b.author")
    List<Book> findAllWithAuthors();
    
    List<Book> findByAuthorId(Long authorId);
    Optional<Book> findByIsbn(String isbn);
}

