package com.projeto.sistema.configuracao;

import com.projeto.sistema.modelo.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfiguracao {
    @Bean(name = "usuario")
    public Usuario usuario() {
        return new Usuario();
    }
}