package com.nikhilmangali1.LibraryAPI.controller;

import com.nikhilmangali1.LibraryAPI.dto.AvailabilityUpdateRequest;
import com.nikhilmangali1.LibraryAPI.model.Book;
import com.nikhilmangali1.LibraryAPI.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        if(book.getTitle() == null || book.getTitle().trim().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id){
        Book book = bookService.getBookById(id);
        if(book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Integer id) {
        if(bookService.deleteBookById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}/availability")
    public ResponseEntity<Book> updateAvailability(
            @PathVariable Integer id,
            @RequestBody AvailabilityUpdateRequest request) {

        Boolean available = request.getAvailable();
        if (available == null) {
            return ResponseEntity.badRequest().build();
        }

        Book updated = bookService.updateAvailability(id, available);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "LibraryAPI is running!";
    }
}
