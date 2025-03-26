package com.projeto.sistema.repositorio;


import com.projeto.sistema.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
