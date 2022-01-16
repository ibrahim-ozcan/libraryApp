package com.example.libraryApp.repository;

import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.Shelf;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class ShelfRepositoryTest {

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private BookRepository  bookRepository;

    @Test
    public void createShelf()
    {
        Shelf shelf = new Shelf();
        shelf.setName("shelf");
        Shelf shelf1 = new Shelf();
        shelf1.setName("shelf1");
        Shelf shelf2 = new Shelf();
        shelf2.setName("shelf2");

        Book book1 = Book.builder().name("book1")
                .author("author1")
                .releaseYear(2015)
                .category("category1")
                .shelf(shelf1)
                .build();
//        shelf1.addBook(book1);

        Book book2 = Book.builder().name("book1")
                .author("author1")
                .releaseYear(2015)
                .category("category1")
                .shelf(shelf2)
                .build();
//        shelf2.addBook(book2);
        
//        shelfRepository.save(shelf1);
//        shelfRepository.save(shelf2);
//        shelfRepository.save(shelf);
        bookRepository.save(book1);
        bookRepository.save(book2);

        Shelf tempShelf = shelfRepository.findById(1L).get();
        System.out.println(tempShelf);
    }

    @Test
    public void getShelf() {
        List<Shelf> shelf = shelfRepository.findAll();
        System.out.println(shelf);
    }

    @Test
    public void getBooks()
    {
        List<Book> books = bookRepository.findAll();
        System.out.println(books);
    }

    @Test
    
    public void removeBook()
    {
        long removeID = 1;
        Book book =bookRepository.findById(removeID).get();
        System.out.println("SHELFS...");
        getShelf();
        System.out.println("Books...");
        getBooks();
        
        System.out.println(String.format("REMOVE BOOK WITH ID: %d",removeID));

        bookRepository.deleteById(removeID);

        System.out.println("SHELFS...");
        getShelf();
        System.out.println("Books...");
        getBooks();


    }


    @Test
    public void removeShelf(){

        Shelf shelf = shelfRepository.findById(2L).get();

        shelfRepository.delete(shelf);


    }

    @Test
    public void addSingleBook()
    {
        Book book = Book.builder().name("book5")
                .author("author1")
                .releaseYear(2015)
                .category("category1")
                .build();

        bookRepository.save(book);
    }


    @Test
    public void addBookToShelf()
    {
        Shelf shelf = shelfRepository.findById(2L).get();
        Book book = bookRepository.findById(4L).get();
        System.out.println(shelf);
        System.out.println(book);

        //just setting books' shelf and saving book to bookrepo makes relation
        //but just calling shelf.addBook() and saving to shelf repo does not create relation
        book.setShelf(shelf);
        
        shelf.addBook(book);

//        bookRepository.save(book);
        shelfRepository.save(shelf);

        shelf = shelfRepository.findById(2L).get();
        book = bookRepository.findById(3L).get();
        System.out.println(shelf);
        System.out.println(book);
    }


    @Test
    public void  updateBook()
    {
//        Book book = bookRepository.findById(2L).get();
//        book.setComment("sdasd");
//        bookRepository.save(book);

        Book book1 = bookRepository.findById(3L).get();
        Shelf shelf = shelfRepository.findById(1L).get();
        book1.setShelf(shelf);
        bookRepository.save(book1);


    }


    @Test
    public void updateShelf()
    {
        Book book1 = bookRepository.findById(5L).get();
        Shelf shelf = shelfRepository.findById(1L).get();
        book1.setShelf(shelf);
        shelf.addBook(book1);
        shelfRepository.save(shelf);

        getShelf();
    }


}

