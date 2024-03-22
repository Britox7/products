package com.kaue.project.products.controllers;

import com.kaue.project.products.entities.Comprador;
import com.kaue.project.products.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Compradores")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<Comprador> findAll(){
        List<Comprador> result = repository.findAll();
        return result;
    }

    @GetMapping(value = "/{id}")
    public Comprador findById(@PathVariable Long id){
        Comprador result = repository.findById(id).get();
        return result;
    }

    @PostMapping
    public Comprador insert(@RequestBody Comprador comprador){
        Comprador result = repository.save(comprador);
        return result;
    }
}
