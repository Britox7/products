package com.kaue.project.products.controllers;

import com.kaue.project.products.entities.Comprador;
import com.kaue.project.products.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Object> findById(@PathVariable Long id){
        try {
            if (repository.existsById(id)) {
                Comprador result = repository.findById(id).get();
                return ResponseEntity.ok(result);
            } else {
                String mensagem = "Usuário não encontrado, forneça um ID válido.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o usuário: " + e.getMessage());
        }
    }

    @PostMapping
    public Comprador insert(@RequestBody Comprador comprador){
        Comprador result = repository.save(comprador);
        return result;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                String message = "Usuário deletado com sucesso";
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário inexistente, forneça um id válido: ");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o usuário: " + e.getMessage());
        }
    }
}
