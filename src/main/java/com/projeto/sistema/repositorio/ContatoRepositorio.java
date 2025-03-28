package com.projeto.sistema.repositorio;


import com.projeto.sistema.modelo.Cliente;
import com.projeto.sistema.modelo.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepositorio extends JpaRepository<Contato, Long> {
}