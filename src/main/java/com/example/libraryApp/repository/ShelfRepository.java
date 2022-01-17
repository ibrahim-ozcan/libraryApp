package com.example.libraryApp.repository;

import com.example.libraryApp.model.CustomUser;
import com.example.libraryApp.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {

    Optional<List<Shelf>> findAllByUser(CustomUser user) ;
    Optional<Shelf> findByUserAndId(CustomUser user, long id);
}
