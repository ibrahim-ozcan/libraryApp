package com.example.libraryApp.controller;

import com.example.libraryApp.model.Book;
import com.example.libraryApp.model.Shelf;
import com.example.libraryApp.repository.ShelfRepository;
import com.example.libraryApp.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelf")
public class ShelfController {



    private ShelfService shelfService;

    @Autowired
    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allshelves")
    public List<Shelf> getAllShelves()
    {
        return shelfService.getAllShelves();
    }

    @GetMapping("/shelfs")
    public List<Shelf> getShelfs()
    {
        return shelfService.getShelves();
    }
    @GetMapping("/shelf/{id}")
    public Shelf getShelf(@PathVariable long id)
    {
        return shelfService.getShelf(id);
    }

    @PostMapping("/addshelf")
    public Shelf addShelf(@RequestBody Shelf shelf)
    {
        return shelfService.addShelf(shelf);
    }

    @PutMapping("/updateshelf")
    public Shelf updateShelf(@RequestBody Shelf shelf)
    {
        return shelfService.updateShelf(shelf);
    }

    @PutMapping("/addbook/{book_id}/{shelf_id}")
    public Shelf addBookToShelf(@PathVariable long book_id,@PathVariable long shelf_id)
    {
        return shelfService.addBookToShelf(book_id,shelf_id);
    }


    @DeleteMapping("/deleteshelf/{id}")
    public String deleteShelf(@PathVariable long id)
    {
        if(shelfService.deleteShelf(id))
            return String.format("Shelf with id %d deleted successfully.",id);
        

        return String.format("There is no such shelf with id = %d.",id);
    }

    
}
