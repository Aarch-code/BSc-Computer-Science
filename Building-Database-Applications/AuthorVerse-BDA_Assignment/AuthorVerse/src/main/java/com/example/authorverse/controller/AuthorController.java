/*PRACHI TANDEL - 2023EBCS178*/

package com.example.authorverse.controller;
import com.example.authorverse.model.Author;
import com.example.authorverse.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Regular authors list
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("viewMode", "normal"); // Different approach using viewMode
        return "authors/author-list";
    }

    // Authors with books
    @GetMapping("/with-books")
    public String listAuthorsWithBooks(Model model) {
        model.addAttribute("authors", authorService.getAllAuthorsWithBooks());
        model.addAttribute("viewMode", "withBooks"); // Changed to viewMode
        return "authors/author-list";
    }

    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add-author";
    }

    // Process author creation
    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author, RedirectAttributes redirectAttributes) {
        try {
            authorService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage", "Author added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding author: " + e.getMessage());
        }
        return "redirect:/authors";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.getAuthorById(id));
        return "authors/edit-author";
    }

    // Process update
    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author, 
                             RedirectAttributes redirectAttributes) {
        try {
            authorService.updateAuthor(id, author);
            redirectAttributes.addFlashAttribute("successMessage", "Author updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating author: " + e.getMessage());
        }
        return "redirect:/authors";
    }

    // Delete author
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthor(id);
            redirectAttributes.addFlashAttribute("successMessage", "Author deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting author: " + e.getMessage());
        }
        return "redirect:/authors";
    }
}