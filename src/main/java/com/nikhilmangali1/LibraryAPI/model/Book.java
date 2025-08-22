package com.nikhilmangali1.LibraryAPI.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Book {


    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    private String author;

    @NotBlank(message = "ISBN cannot be empty")
    @Pattern(regexp = "^[0-9]{13}$", message = "ISBN must be a 13-digit number")
    private String isbn;

    @NotNull(message = "Availability status must be provided")
    private Boolean available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
