package com.nikhilmangali1.LibraryAPI.repo;

import com.nikhilmangali1.LibraryAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
