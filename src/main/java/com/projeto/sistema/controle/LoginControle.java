package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Usuario;
import com.projeto.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginControle {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/login")
    ModelAndView login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        Boolean ehUsuarioCorreto = usuarioRepositorio.existsByUsernameAndSenha(username, password);
        if (ehUsuarioCorreto) {
            Usuario usuario = usuarioRepositorio.findByUsername(username);
            ModelAndView mv = new ModelAndView("administrativo/areaLogada");
            mv.addObject("usuario", usuario);
            return mv;
        }
        ModelAndView mv = new ModelAndView("administrativo/login");
        mv.addObject("mensagemDeErro", "Login Incorreto");
        return mv;
    }
}