package com.kaue.project.products.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_comprador")
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int dinheiroDisp;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Produtos.class)
    @JoinColumn(name = "product_id", insertable = true, updatable=true, nullable = false)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Produtos produtos;

    public Comprador(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDinheiroDisp() {
        return dinheiroDisp;
    }

    public void setDinheiroDisp(int dinheiroDisp) {
        this.dinheiroDisp = dinheiroDisp;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }
}

