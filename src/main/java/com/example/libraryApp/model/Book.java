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
    @Column(name = "id")
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
    
//    @JoinTable(
//            name = "bookshelf",
//            joinColumns = @JoinColumn(name = "bookkk_id"),
//            inverseJoinColumns = @JoinColumn(name = "shelffff_id")
//    )
    @JsonIgnoreProperties("books")
    private Shelf shelf;

    @PreRemove
    public void removeShelf()
    {
        if(shelf!=null)
            shelf.removeBook(this);
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































