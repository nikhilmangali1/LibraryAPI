package com.nikhilmangali1.LibraryAPI.integration;

import com.nikhilmangali1.LibraryAPI.model.Book;
import com.nikhilmangali1.LibraryAPI.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

//    @BeforeEach
//    void setup() {
//        bookRepository.deleteAll();
//    }

    @Test
    void testAddAndGetBook() throws Exception {
        String bookJson = """
            {
              "title": "Effective Java",
              "author": "Joshua Bloch",
              "isbn": "9780134685991",
              "available": true
            }
        """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Effective Java"));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setTitle("The Pragmatic Programmer");
        book.setAuthor("Andrew Hunt");
        book.setIsbn("9780201616224");
        book.setAvailable(true);

        Book savedBook = bookRepository.save(book);

        mockMvc.perform(delete("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isNotFound());
    }
}
