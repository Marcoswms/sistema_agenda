package com.projeto.sistema.repositorio;

import com.projeto.sistema.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Boolean existsByUsernameAndSenha(String username, String senha);
    Usuario findByUsername(String username);
}