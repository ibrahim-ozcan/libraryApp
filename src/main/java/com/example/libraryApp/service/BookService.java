package com.example.libraryApp.service;


import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.CustomUser;
import com.example.libraryApp.model.Shelf;
import com.example.libraryApp.repository.BookRepository;
import com.example.libraryApp.repository.CustomUserRepository;
import com.example.libraryApp.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private ShelfRepository shelfRepository;
    private CustomUserRepository customUserRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ShelfRepository shelfRepository, CustomUserRepository customUserRepository) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.customUserRepository = customUserRepository;
    }

    //admin
    public List<Book>  getAllBooks()
    {
        return bookRepository.findAll();
    }

    public List<Book> getBooks() {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        return bookRepository.findAllByUser(customUser).get();

    }

    public Book getBook(Long id) {

        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        Optional<Book> opt = bookRepository.findByUserAndId(customUser,id);
        if(opt.isPresent())
            return opt.get();
        return null;
    }

    public Book addBook(Book book) {

        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        customUser.getBooks().add(book);
        book.setUser(customUser);

        return customUserRepository.save(customUser).getBooks().get(customUserRepository.save(customUser).getBooks().size()-1);
    }

    public Book updateBook(Book book) {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();

        if((book.getUser()!=null && book.getUser().getUsername().equals(getUsername())) ||
                customUser.getBooks().stream().anyMatch(asd -> asd.getId()==book.getId())  )
            return bookRepository.save(book) ;

        return  null;
    }

    public boolean deleteBook(long id) {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        Optional<Book> book = bookRepository.findByUserAndId(customUser,id);
        if(book.isPresent())
        {
            bookRepository.deleteById(id);
            return true;
        }
        return  false;
    }


    public Book putBookToShelf(long book_id, long shelf_id) {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        Optional<Book> book = bookRepository.findByUserAndId(customUser,book_id);
        Optional<Shelf> shelf = shelfRepository.findByUserAndId(customUser,shelf_id);
        if(book.isPresent()  && shelf.isPresent())
        {
            book.get().setShelf(shelf.get());
            bookRepository.save(book.get());
            return book.get();
        }
        return null;
    }
    public String getUsername()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }
}
