package com.projeto.sistema.repositorio;

import com.projeto.sistema.modelo.Perfil;
import com.projeto.sistema.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Boolean existsByUsernameAndSenha(String username, String senha);
    Usuario findByUsername(String username);
    List<Usuario> findByPerfil(Perfil perfil);//Usado 'List' para que possa ser percorrido com 'each' no 'listaUsuario'
}