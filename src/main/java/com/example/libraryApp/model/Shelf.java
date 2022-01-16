package com.example.libraryApp.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "shelf")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelf_id")
    private long shelf_id;

    private String name;

    /*{CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH}
     */

    @OneToMany(mappedBy = "shelf", cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)

    private List<Book> books = new ArrayList<>();


    public void removeBook(Book book)
    {
        this.books.remove(book);
    }

    @PreRemove
    public void removeBooks()
    {
        this.books.forEach(book-> book.removeShelf(this));
        this.books=null;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(Book book) {
        books.remove(book);
    }


    @Override
    public String toString() {
        return "Shelf{" + "id=" + shelf_id + ", name='" + name + '\'' + ", books=" + books + '}';
    }
}
