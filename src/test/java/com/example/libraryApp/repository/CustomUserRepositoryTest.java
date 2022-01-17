package com.example.libraryApp.repository;

import com.example.libraryApp.model.Authority;
import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.CustomUser;
import com.example.libraryApp.model.Shelf;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserRepositoryTest {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void addUser()
    {
        CustomUser customUser = new CustomUser();
        customUser.setUsername("user1");
        customUser.setPassword("pass1");
        customUser = customUserRepository.save(customUser)   ;
        System.out.println(customUser);
        CustomUser customUser2 = new CustomUser();
        customUser2.setUsername("user2");
        customUser2.setPassword("pass2");
        customUser2 = customUserRepository.save(customUser2)   ;
        System.out.println(customUser);
    }

    @Test
    public void addAuthority()
    {
        Authority userAuthority = authorityRepository.save(new Authority(null, "USER", new HashSet<>()));
        Authority adminAuthority = authorityRepository.save(new Authority(null, "ADMIN", new HashSet<>()));
        CustomUser customUser = customUserRepository.findByUsername("user1").get()   ;
        customUser.setAuthorities(Set.of(userAuthority));
        CustomUser adminUser = customUserRepository.findByUsername("user2").get()   ;
        adminUser.setAuthorities(Set.of(adminAuthority));
        customUserRepository.save(customUser)     ;
        customUserRepository.save(adminUser)     ;
    }

    @Test
    public void addBookToUser()
    {
        Book book = new Book();
        book.setName("userbook1");
        book.setAuthor("auth1");

        CustomUser user = customUserRepository.findById(1L).get();
        user.getBooks().add(book);
        book.setUser(user);
        customUserRepository.save(user);

        Book book1 = new Book();
        book1.setName("userbook2");
        book1.setAuthor("auth2");

        CustomUser user1 = customUserRepository.findById(2L).get();
        user1.getBooks().add(book1);
        book1.setUser(user1);
        customUserRepository.save(user1);

        book1 = new Book();
        book1.setName("userbook3");
        book1.setAuthor("auth3");

        user1 = customUserRepository.findById(2L).get();
        user1.getBooks().add(book1);
        book1.setUser(user1);
        customUserRepository.save(user1);

        book1 = new Book();
        book1.setName("userbook4");
        book1.setAuthor("auth4");

        user1 = customUserRepository.findById(1L).get();
        user1.getBooks().add(book1);
        book1.setUser(user1);
        customUserRepository.save(user1);

    }

    @Test
    public void addShelfToUser()
    {
        Shelf shelf = new Shelf();
        shelf.setName("shelf1");

        CustomUser user = customUserRepository.findById(1L).get();
        user.getShelves().add(shelf);
        shelf.setUser(user);

        customUserRepository.save(user);

        shelf = new Shelf();
        shelf.setName("shelf2");

        user = customUserRepository.findById(2L).get();
        user.getShelves().add(shelf);
        shelf.setUser(user);

        customUserRepository.save(user);
    }

    @Test
    public void populateExample()
    {
         addUser();
         addBookToUser();
         addShelfToUser();
        addBookToShelf();
    }

    @Test
    public void addBookToShelf()
    {
        Shelf shelf =      shelfRepository.findById(1L).get();
        Book book = bookRepository.findById(1L).get();

        book.setShelf(shelf);
        bookRepository.save(book);

        shelf =      shelfRepository.findById(1L).get();
        book = bookRepository.findById(4L).get();

        book.setShelf(shelf);
        bookRepository.save(book);

        shelf =      shelfRepository.findById(2L).get();
        book = bookRepository.findById(2L).get();

        book.setShelf(shelf);
        bookRepository.save(book);


//        customUser.getShelves().add(shelf);


//        Set<Book> books =  customUser.getBooks();
//
//        for(Iterator<Book> iter = books.iterator(); iter.hasNext();)
//        {
//            iter.next().setShelf(shelf);
//        }


//        customUserRepository.save(customUser);

    }


    @Test
    public void deleteUser()
    {
        CustomUser customUser = customUserRepository.findById(1L).get();

        customUserRepository.delete(customUser);
    }

    @Test
    public void deleteBook()
    {
        Book book = bookRepository.findById(2L) .get();
        bookRepository.delete(book);
    }

    @Test
    public void findBookByUser()
    {
//        CustomUser user = customUserRepository.findByUsername("user1") .get() ;
//        List<Book> books = bookRepository.findAllByUser(user).get();
//        System.out.println(books);
    }
}