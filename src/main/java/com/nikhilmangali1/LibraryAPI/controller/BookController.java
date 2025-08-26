package com.nikhilmangali1.LibraryAPI.controller;

import com.nikhilmangali1.LibraryAPI.dto.AvailabilityUpdateRequest;
import com.nikhilmangali1.LibraryAPI.model.Book;
import com.nikhilmangali1.LibraryAPI.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
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
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable Integer id){
        Optional<Book> book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Integer id) {
        boolean deleted = bookService.deleteBookById(id);
        if (deleted) {
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

    @PostMapping("/batch")
    public ResponseEntity<List<Book>> addBooks(@Valid @RequestBody List<Book> books){
        if(books == null || books.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        List<Book> savedBooks = books.stream()
                .filter(book -> book.getTitle() != null && !book.getTitle().trim().isBlank())
                .map(bookService::addBook)
                .toList();

        return ResponseEntity.ok(savedBooks);
    }

    @GetMapping("field/{field}")
    public ResponseEntity<List<Book>> getAllBooksWithSortingByField(@PathVariable String field,
                                                                    @RequestParam(defaultValue = "asc") String direction){
        List<Book> sortedBooks = bookService.getAllBooksWithSortingByField(field, direction);
        return ResponseEntity.ok(sortedBooks);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "LibraryAPI is running!";
    }
}
