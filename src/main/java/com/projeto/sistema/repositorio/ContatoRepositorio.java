package com.projeto.sistema.repositorio;

import com.projeto.sistema.modelo.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContatoRepositorio extends JpaRepository<Contato, Long> {
    List<Contato> findByCliente_UsuarioId(Long usuarioId);
}