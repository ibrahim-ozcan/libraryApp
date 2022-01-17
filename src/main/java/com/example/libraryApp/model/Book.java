package com.example.libraryApp.model;


import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    private String category;

    private Integer releaseYear;

    private String publisher;

    private Integer rate;

    private String comment;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "shelf_id", referencedColumnName = "shelf_id")
    @JsonIgnoreProperties({"books", "user"})
    private Shelf shelf;


    @ManyToOne
    @JsonIgnoreProperties({"books", "shelves"})
    private CustomUser user;

    @PreRemove
    public void removeBookFromShelf()
    {
        if(shelf!=null)
            shelf.removeBook(this);
        if(user!=null)
            this.user.getBooks().remove(this);
        this.user=null;
        this.shelf=null;
    }

    public void removeShelf(Shelf shelf)
    {
        this.shelf=null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", releaseYear=" + releaseYear +
                ", publisher='" + publisher + '\'' +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                //", shelf=" + shelf +
                '}';
    }
}































