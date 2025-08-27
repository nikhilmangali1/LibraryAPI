package com.nikhilmangali1.LibraryAPI.service;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nikhilmangali1.LibraryAPI.repo.BookRepository;
import org.springframework.web.server.ResponseStatusException;

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
        return Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")));
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

    public List<Book> getAllBooksWithSortingByField(String field, String direction){
        Sort sort = "asc".equalsIgnoreCase(direction) ?
                Sort.by(Sort.Direction.ASC, field) : Sort.by(Sort.Direction.DESC, field);
        return bookRepository.findAll(sort);
    }

    public Page<Book> getBooksWithPagination(int offset, int pageSize) {
        return bookRepository.findAll(PageRequest.of(offset,pageSize));
    }

    public Page<Book> getBooksWithPaginationAndSorting(int offset, int pageSize, String field) {
        return bookRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(Sort.Direction.ASC, field)));
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.getByIsbn(isbn);
    }

    public List<Book> getBooksByAvailability(boolean available) {
        if(available){
            return bookRepository.getByAvailableTrue();
        }
        return bookRepository.getByAvailableFalse();
    }

    public List<Book> getAllBooksWhereTitleContainingKeyword(String keyword) {
        return bookRepository.getByTitleContaining(keyword);
    }

    public List<Book> getBooksByKeyword(String keyword) {
        return bookRepository.searchByKeyword(keyword);
    }
}
