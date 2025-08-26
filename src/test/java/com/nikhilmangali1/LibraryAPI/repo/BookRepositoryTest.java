package com.nikhilmangali1.LibraryAPI.repo;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void cleanDb() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Test saving a book")
    void testSaveBook(){
        Book book = new Book();
        book.setAuthor("Yashwanth Kenetkar");
        book.setTitle("Effective Java");
        book.setIsbn("1234873645827");
        book.setAvailable(true);


        Book savedBook = bookRepository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("Yashwanth Kenetkar", savedBook.getAuthor());
        assertTrue(savedBook.getAvailable());
    }

    @Test
    @DisplayName("Test find all books")
    void testFindAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Java Basics");
        book1.setAuthor("Author1");
        book1.setIsbn("1234567890123");
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setTitle("Spring Boot Guide");
        book2.setAuthor("Author2");
        book2.setIsbn("1234567890124");
        book2.setAvailable(false);

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();

        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Test find by ID")
    void testFindById() {
        Book book = new Book();
        book.setTitle("Hibernate in Action");
        book.setAuthor("Author3");
        book.setIsbn("1234567890125");
        book.setAvailable(true);

        Book savedBook = bookRepository.save(book);

        Optional<Book> found = bookRepository.findById(savedBook.getId());

        assertTrue(found.isPresent());
        assertEquals("Hibernate in Action", found.get().getTitle());
    }

    @Test
    @DisplayName("Test delete by ID")
    void testDeleteById() {
        Book book = new Book();
        book.setTitle("Test Driven Development: By Example");
        book.setAuthor("Kent Beck");
        book.setIsbn("9780321146533");
        book.setAvailable(true);

        Book savedBook = bookRepository.save(book);

        bookRepository.deleteById(savedBook.getId());

        Optional<Book> deleted = bookRepository.findById(savedBook.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("Test update availability")
    void testUpdateAvailability() {
        Book book = new Book();
        book.setTitle("Refactoring");
        book.setAuthor("Martin Fowler");
        book.setIsbn("9780201485677");
        book.setAvailable(true);

        Book savedBook = bookRepository.save(book);

        savedBook.setAvailable(false);
        Book updated = bookRepository.save(savedBook);

        assertFalse(updated.getAvailable());
    }
}
