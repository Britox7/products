package com.kaue.project.products.controllers;
import com.kaue.project.products.entities.Produtos;
import com.kaue.project.products.repositories.ProdutosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/Produtos")
public class ProdutosController {

    @Autowired
    private ProdutosRepository repository;

    @GetMapping
    public List<Produtos> findAll(){
        List<Produtos> result = repository.findAll();
        return result;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        try {
            if (repository.existsById(id)){
                Produtos result = repository.findById(id).get();
                return ResponseEntity.ok(result);
            } else {
                String mensagem = "Produto não encontrado, forneça um ID válido.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o usuário: " + e.getMessage());
        }
    }

    @PostMapping
    public Produtos insert(@RequestBody Produtos produtos){
        Produtos result = repository.save(produtos);
        return result;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                String message = "Produto deletado com sucesso";
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto inexistente, forneça um id válido: ");
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao excluir o produto: " + e.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduto(@RequestBody Produtos data) {
        try {
            Optional<Produtos> optionalProduto = repository.findById(data.getId());
            if (optionalProduto.isPresent()) {
                Produtos produto = optionalProduto.get();
                produto.setName(data.getName());
                produto.setPreco(data.getPreco());
                repository.save(produto);
                return ResponseEntity.ok(produto);
            } else {
                throw new EntityNotFoundException("Produto não encontrado para o ID: " + data.getId());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno ao atualizar o produto: " + e.getMessage());
        }
    }

}
