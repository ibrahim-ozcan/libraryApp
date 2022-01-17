package com.example.libraryApp.repository;

import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {


    Optional<List<Book>> findAllByUser(CustomUser user);
    Optional<Book> findByUserAndId(CustomUser user,long id);
}
