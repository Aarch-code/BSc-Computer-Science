/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.controller;
import com.example.authorverse.model.Author;
import com.example.authorverse.model.Book;
import com.example.authorverse.service.AuthorService;
import com.example.authorverse.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/books")
public class BookController {
   private final BookService bookService;
   private final AuthorService authorService;


   @Autowired
   public BookController(BookService bookService, AuthorService authorService) {
       this.bookService = bookService;
       this.authorService = authorService;
   }


   @GetMapping
   public String listBooks(Model model) {
       model.addAttribute("books", bookService.getAllBooksWithAuthors());
       return "books/book-list";
   }


   @GetMapping("/add")
   public String showAddForm(Model model) {
       model.addAttribute("book", new Book());
       model.addAttribute("authors", authorService.getAllAuthors());
       return "books/add-book";
   }

   @PostMapping("/add")
   public String addBook(@ModelAttribute Book book, @RequestParam Long authorId, RedirectAttributes redirectAttributes) {
       try {
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
       } catch (IllegalArgumentException e) {
           // Catch the IllegalArgumentException and display a user-friendly error message
           redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
       }
       return "redirect:/books";
   }


   @GetMapping("/edit/{id}")
   public String showEditForm(@PathVariable Long id, Model model) {
       model.addAttribute("book", bookService.getBookById(id));
       model.addAttribute("authors", authorService.getAllAuthors());
       return "books/edit-book";
   }


   @PostMapping("/edit/{id}")
   public String updateBook(@PathVariable Long id, @ModelAttribute Book book,
                          @RequestParam Long authorId, RedirectAttributes redirectAttributes) {
       try {
           Author author = authorService.getAuthorById(authorId);
           book.setAuthor(author);
           bookService.updateBook(id, book);
           redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
       } catch (Exception e) {
           redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
       }
       return "redirect:/books";
   }


   @GetMapping("/delete/{id}")
   public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
       try {
           bookService.deleteBook(id);
           redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
       } catch (Exception e) {
           redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + e.getMessage());
       }
       return "redirect:/books";
   }


   @GetMapping("/by-author/{authorId}")
   public String listBooksByAuthor(@PathVariable Long authorId, Model model) {
       model.addAttribute("books", bookService.getBooksByAuthorId(authorId));
       model.addAttribute("author", authorService.getAuthorById(authorId));
       return "books/book-list";
   }
}
