package com.example.libraryApp.repository;

import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.Shelf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShelfRepository shelfRepository;


    @Test
    public void createBook()
    {
        Optional<Shelf> shelf = shelfRepository.findById(2L);
        Book book = Book.builder().name("book4")
                .author("author2")
                .releaseYear(2013)
                .category("category1")
                .build();
        bookRepository.save(book);
        book = Book.builder().name("book5")
                .author("author2")
                .releaseYear(2013)
                .category("category1")
                .build();
        bookRepository.save(book);
    }

}