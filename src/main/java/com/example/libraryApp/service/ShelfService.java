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
public class ShelfService {


    private ShelfRepository shelfRepository;
    private BookRepository bookRepository;

    @Autowired
    public ShelfService(ShelfRepository shelfRepository, BookRepository bookRepository) {
        this.shelfRepository = shelfRepository;
        this.bookRepository = bookRepository;
    }


    public List<Shelf> getShelfs() {
        return shelfRepository.findAll();
    }

    public Shelf getShelf(long id)
    {
        Optional<Shelf> shelf = shelfRepository.findById(id);
        if(shelf.isPresent())
            return shelf.get();
        return null;
    }


    public Shelf addShelf(Shelf shelf) {

        return shelfRepository.save(shelf);
    }

    public Shelf updateShelf(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    public Shelf addBookToShelf(long book_id, long shelf_id) {
        Optional<Book> book = bookRepository.findById(book_id);
        Optional<Shelf> shelf = shelfRepository.findById(shelf_id);
        if(book.isPresent()  && shelf.isPresent())
        {
            book.get().setShelf(shelf.get());
            shelf.get().addBook(book.get());
            bookRepository.save(book.get());

            return shelf.get();
        }
        return null;
    }

    public boolean deleteShelf(long id) {
        Shelf shelf = shelfRepository.findById(id).get();
        if(shelf != null)
        {
            shelfRepository.deleteById(id);
            return true;
        }
        return  false;
    }
}
