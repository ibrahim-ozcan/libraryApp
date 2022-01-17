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
public class ShelfService {


    private ShelfRepository shelfRepository;
    private BookRepository bookRepository;
    private CustomUserRepository customUserRepository;

    @Autowired
    public ShelfService(ShelfRepository shelfRepository, BookRepository bookRepository, CustomUserRepository customUserRepository) {
        this.shelfRepository = shelfRepository;
        this.bookRepository = bookRepository;
        this.customUserRepository = customUserRepository;
    }



    public List<Shelf> getAllShelves() {
        return shelfRepository.findAll();
    }


    public List<Shelf> getShelves() {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        return shelfRepository.findAllByUser(customUser).get();
    }

    public Shelf getShelf(long id)
    {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        Optional<Shelf> shelf = shelfRepository.findByUserAndId(customUser,id);
        if(shelf.isPresent())
            return shelf.get();
        return null;
    }


    public Shelf addShelf(Shelf shelf) {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        shelf.setUser(customUser);
        customUser.getShelves().add(shelf);

        return customUserRepository.save(customUser).getShelves().get(customUserRepository.save(customUser).getShelves().size()-1) ;
    }

    public Shelf updateShelf(Shelf shelf) {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();

        if((shelf.getUser()!=null && shelf.getUser().getUsername().equals(getUsername())) ||
                customUser.getShelves().stream().anyMatch(asd -> asd.getId()==shelf.getId())  )
            return shelfRepository.save(shelf) ;

        return  null;
    }

    public Shelf addBookToShelf(long book_id, long shelf_id) {
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        Optional<Book> book = bookRepository.findByUserAndId(customUser,book_id);
        Optional<Shelf> shelf = shelfRepository.findByUserAndId(customUser,shelf_id);
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
        CustomUser customUser = customUserRepository.findByUsername(getUsername()).get();
        Optional<Shelf> shelf = shelfRepository.findByUserAndId(customUser,id);
        if(shelf.isPresent())
        {
            shelfRepository.deleteById(id);
            return true;
        }
        return  false;
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
