package com.nikhilmangali1.LibraryAPI.service;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookService {
    private final Map<Integer, Book> books = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Book addBook(Book book){
        int id = idCounter.getAndIncrement();
        book.setId(id);
        books.put(id,book);
        return book;
    }

    public List<Book> getAllBooks(){
        return new ArrayList<>(books.values());
    }

    public Book getBookById(Integer id){
        return books.get(id);
    }

    public boolean deleteBookById(Integer id){
        return books.remove(id) != null;
    }

    public Book updateAvailability(Integer id, boolean available){
        Book book = books.get(id);
        if(book != null){
            book.setAvailable(available);
        }
        return book;
    }
}
