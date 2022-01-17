package com.example.libraryApp.service;


import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.Shelf;
import com.example.libraryApp.repository.BookRepository;
import com.example.libraryApp.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private ShelfRepository shelfRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ShelfRepository shelfRepository) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        Optional<Book> opt = bookRepository.findById(id);
        if(opt.isPresent())
            return opt.get();
        return null;
    }

    public Book addBook(Book book) {

        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
           return bookRepository.save(book) ;
    }

    public boolean deleteBook(long id) {
        Book book = getBook(id);
        if(book != null)
        {
            bookRepository.deleteById(id);
            return true;
        }
        return  false;
    }


    public Book putBookToShelf(long book_id, long shelf_id) {
        Optional<Book> book = bookRepository.findById(book_id);
        Optional<Shelf> shelf = shelfRepository.findById(shelf_id);
        if(book.isPresent()  && shelf.isPresent())
        {
            book.get().setShelf(shelf.get());
            bookRepository.save(book.get());
            return book.get();
        }
        return null;
    }
}
