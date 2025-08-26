package com.nikhilmangali1.LibraryAPI.service;

import com.nikhilmangali1.LibraryAPI.model.Book;
import com.nikhilmangali1.LibraryAPI.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    // add book
    @Test
    void addBookShouldReturnSavedBook() {
        Book book = new Book();
        book.setTitle("Java Basics");                       // Arrange

        when(bookRepository.save(any(Book.class))).thenReturn(book);        // Mock

        Book saved = bookService.addBook(book);                     // Act

        assertNotNull(saved);                                       // Assert
        assertEquals("Java Basics",saved.getTitle());
        verify(bookRepository, times(1)).save(book);        // verify
    }


    // get all books
    @Test
    void getAllBooksShouldReturnListOfBooks(){
        Book book1 = new Book();
        book1.setTitle("Java");

        Book book2 = new Book();
        book2.setTitle("Spring");


        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1,book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2,books.size());
        assertEquals("Java",books.getFirst().getTitle());
        verify(bookRepository,times(1)).findAll();
    }

    @Test
    void getAllBooksShouldReturnEmptyList(){
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<Book> books = bookService.getAllBooks();

        assertTrue(books.isEmpty());
        verify(bookRepository,times(1)).findAll();
    }


    // get book by id
    @Test
    void getBookByIdShouldReturnBookIfExists(){
        Book book = new Book();
        book.setId(1);
        book.setTitle("DSA");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Optional<Book> found = bookService.getBookById(1);

        assertEquals(1,found.get().getId());
        assertEquals("DSA",found.get().getTitle());
        verify(bookRepository,times(1)).findById(1);
    }


    @Test
    void getBookByIdShouldReturnNullIfNotExists(){
        when(bookRepository.findById(99)).thenReturn(null);

        Optional<Book> found = bookService.getBookById(99);

        assertNull(found);
        verify(bookRepository,times(1)).findById(99);
    }


    // delete book by id
    @Test
    void deleteBookByIdShouldReturnTrueIfBookExists(){
        when(bookRepository.existsById(1)).thenReturn(true);

        boolean result = bookService.deleteBookById(1);

        assertTrue(result);
        verify(bookRepository, times(1)).existsById(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteBookByIdShouldReturnFalseIfBookNotExists() {
        when(bookRepository.existsById(99)).thenReturn(false);

        boolean result = bookService.deleteBookById(99);

        assertFalse(result);
        verify(bookRepository, times(1)).existsById(99);
        verify(bookRepository, never()).deleteById(any());
    }


    // update availability
    @Test
    void updateAvailabilityShouldUpdateAndReturnBook() {
        Book book = new Book();
        book.setId(1);
        book.setAvailable(false);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updated = bookService.updateAvailability(1, true);

        assertNotNull(updated);
        assertEquals(1, updated.getId());
        assertTrue(updated.getAvailable());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateAvailabilityShouldReturnNullIfBookNotExists() {
        when(bookRepository.findById(99)).thenReturn(Optional.empty());

        Book updated = bookService.updateAvailability(99, true);

        assertNull(updated);
        verify(bookRepository, times(1)).findById(99);
        verify(bookRepository, never()).save(any());
    }

}
