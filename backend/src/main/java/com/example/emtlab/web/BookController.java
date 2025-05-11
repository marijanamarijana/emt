package com.example.emtlab.web;

import com.example.emtlab.dto.*;
import com.example.emtlab.model.views.BooksPerAuthorView;
import com.example.emtlab.model.views.GoodBooksView;
import com.example.emtlab.service.application.BookApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController {
    private final BookApplicationService bookService;

    public BookController(BookApplicationService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves a list of all available books.")
    public List<BookDisplayDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its unique ID.")
    public ResponseEntity<BookDisplayDto> findById(@PathVariable Long id) {
        return bookService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new book", description = "Creates a new book record with the provided details.")
    public ResponseEntity<BookDisplayDto> save(@RequestBody BookUpdateDto book) {
        return bookService.save(book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Update a book", description = "Updates an existing book's details by its ID.")
    public ResponseEntity<BookDisplayDto> update(@PathVariable Long id, @RequestBody BookUpdateDto book) {
        return bookService.update(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a book", description = "Removes a book from the system by its ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookService.findById(id).isPresent()) {
            bookService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get books by author", description = "Returns a list of the number of books by each author")
    @GetMapping("/by-author")
    public ResponseEntity<List<BooksPerAuthorView>> getBooksByAuthor() {
        return ResponseEntity.ok(bookService.getBooksPerAuthorStats());
    }

    @PatchMapping("/rented/{id}")
    @Operation(summary = "Mark book as rented", description = "Marks a book as rented by its ID.")
    public ResponseEntity<BookDisplayDto> rentBook(@PathVariable Long id) {
        return bookService.markRented(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // from lab 1 extra
    @GetMapping("/related/{id}")
    @Operation(summary = "Get related books", description = "Retrieves books related to the given book ID.")
    public ResponseEntity<List<BookRelatedDto>> getRelatedBooks(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getRelated(id));
    }

    // from lab 3 extra
    @GetMapping("/goodBooks")
    public ResponseEntity<List<GoodBooksView>> getGoodBooks(){
        return ResponseEntity.ok(bookService.getGoodBooks());
    }

}
