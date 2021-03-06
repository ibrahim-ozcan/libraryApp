package com.example.libraryApp.controller;

import com.example.libraryApp.model.Book;
import com.example.libraryApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {



    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allbooks")
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();
    }


    @GetMapping("/books")
    public List<Book> getBooks()
    {
        return bookService.getBooks();

    }
    @GetMapping("/booksDeneme")
        public List<Book> getBooksDeneme()
        {
            return bookService.getBooks();

        }

    @GetMapping("/book/{book_id}")
    public Book getBook(@PathVariable long book_id)
    {
        return bookService.getBook(book_id);
    }

    @PostMapping("/addbook")
    public Book addBook(@RequestBody Book book)
    {
        return bookService.addBook(book);
    }



    @PutMapping("/updatebook")
    public Book updateBook(@RequestBody Book book)
    {
          return bookService.updateBook(book);
    }

    @PutMapping("/addshelf/{book_id}/{shelf_id}")
    public Book putBookToShelf(@PathVariable long shelf_id, @PathVariable long book_id)
    {
        return bookService.putBookToShelf(book_id,shelf_id);
    }

    @DeleteMapping("deletebook/{id}")
    public String deleteBook(@PathVariable long id)
    {
        if(bookService.deleteBook(id))
            return String.format("Book with id %d deleted successfully.",id);
        return String.format("There is no such book with id = %d.",id);
    }



}
