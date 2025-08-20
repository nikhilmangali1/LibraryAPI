package com.nikhilmangali1.LibraryAPI.repo;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BookRepository {
    private final Map<Integer, Book> books = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public Book save(Book book){
        int id = idCounter.getAndIncrement();
        book.setId(id);
        books.put(id,book);
        return book;
    }

    public List<Book> findAll(){
        return new ArrayList<>(books.values());
    }

    public Book findById(Integer id){
        return books.get(id);
    }

    public Boolean deleteById(Integer id){
        return books.remove(id) != null;
    }

    public Book updateAvailability(Integer id, boolean availability){
        Book book = books.get(id);
        if(book != null){
            book.setAvailable(availability);
        }
        return book;
    }
}
