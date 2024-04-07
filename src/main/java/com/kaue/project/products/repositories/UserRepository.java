package com.kaue.project.products.repositories;
import com.kaue.project.products.entities.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Comprador, Long> {
}
