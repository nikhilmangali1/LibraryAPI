package com.nikhilmangali1.LibraryAPI.service;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.stereotype.Service;
import com.nikhilmangali1.LibraryAPI.repo.BookRepository;

import java.util.List;
import java.util.Optional;


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

    public Optional<Book> getBookById(Integer id){
        return bookRepository.findById(id);
    }

    public boolean deleteBookById(Integer id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Book updateAvailability(Integer id, boolean available){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setAvailable(available);
            return bookRepository.save(book);
        }
        return null;
    }
}
