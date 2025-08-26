package com.nikhilmangali1.LibraryAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhilmangali1.LibraryAPI.dto.AvailabilityUpdateRequest;
import com.nikhilmangali1.LibraryAPI.model.Book;
import com.nikhilmangali1.LibraryAPI.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Book book;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();

        book = new Book();
        book.setId(1);
        book.setTitle("Spring Boot in Action");
        book.setAuthor("Craig Walls");
        book.setIsbn("1234567890123");
        book.setAvailable(true);
    }



    // add book
    @Test
    void addBookSuccess() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON) // this is correct
                        .content(objectMapper.writeValueAsString(book))) // pass JSON string as content
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot in Action"));
    }


    @Test
    void addBookInvalidTitleReturnsBadRequest() throws Exception {
        book.setTitle("  "); // blank title
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());
    }


    // get all books
    @Test
    void getAllBooksReturnsBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Spring Boot in Action"));
    }

    // get book by id
    @Test
    void getBookById_Found() throws Exception {
        when(bookService.getBookById(1)).thenReturn(Optional.ofNullable(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Spring Boot in Action"));
    }

    @Test
    void getBookById_NotFound() throws Exception {
        when(bookService.getBookById(2)).thenReturn(null);

        mockMvc.perform(get("/api/books/2"))
                .andExpect(status().isNotFound());
    }


    // delete book by id
    @Test
    void deleteBookById_Success() throws Exception {
        when(bookService.deleteBookById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBookById_NotFound() throws Exception {
        when(bookService.deleteBookById(2)).thenReturn(false);

        mockMvc.perform(delete("/api/books/2"))
                .andExpect(status().isNotFound());
    }


    // update availability
    @Test
    void updateAvailability_Success() throws Exception {
        AvailabilityUpdateRequest request = new AvailabilityUpdateRequest();
        request.setAvailable(false);

        book.setAvailable(false);
        when(bookService.updateAvailability(1, false)).thenReturn(book);

        mockMvc.perform(patch("/api/books/1/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void updateAvailability_BadRequest_NullValue() throws Exception {
        AvailabilityUpdateRequest request = new AvailabilityUpdateRequest(); // null

        mockMvc.perform(patch("/api/books/1/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAvailability_NotFound() throws Exception {
        AvailabilityUpdateRequest request = new AvailabilityUpdateRequest();
        request.setAvailable(false);

        when(bookService.updateAvailability(2, false)).thenReturn(null);

        mockMvc.perform(patch("/api/books/2/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

}
