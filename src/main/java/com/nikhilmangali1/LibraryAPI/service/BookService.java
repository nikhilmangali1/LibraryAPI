package com.nikhilmangali1.LibraryAPI.service;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.stereotype.Service;
import com.nikhilmangali1.LibraryAPI.repo.BookRepository;

import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book){
       return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id){
        return bookRepository.findById(id);
    }

    public boolean deleteBookById(Integer id){
        return bookRepository.deleteById(id);
    }

    public Book updateAvailability(Integer id, boolean available){
        return bookRepository.updateAvailability(id, available);
    }
}
