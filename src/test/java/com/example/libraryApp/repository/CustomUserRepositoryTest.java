package com.example.libraryApp.repository;

import com.example.libraryApp.model.Authority;
import com.example.libraryApp.model.CustomUser;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserRepositoryTest {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void addUser()
    {
        CustomUser customUser = new CustomUser(null,"user2","pass2", Set.of());
        customUser = customUserRepository.save(customUser)   ;
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

}