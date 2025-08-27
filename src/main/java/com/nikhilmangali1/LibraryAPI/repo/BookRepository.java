package com.nikhilmangali1.LibraryAPI.repo;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByAuthor(String author);
    List<Book> findByTitle(String title);
    Book getByIsbn(String isbn);
    List<Book> getByAvailableTrue();
    List<Book> getByAvailableFalse();
    List<Book> getByTitleContaining(String keyword);

    // JPQL = Java Persistence Query Language - Custom JPA Filter
    @Query(
            "SELECT b FROM Book b WHERE "+
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%' )) OR "+
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%' )) OR "+
            "b.isbn LIKE CONCAT('%', :keyword, '%')"
    )
    List<Book> searchByKeyword(@Param("keyword") String keyword);
}
