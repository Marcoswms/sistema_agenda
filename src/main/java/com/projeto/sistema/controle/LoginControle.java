package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Usuario;
import com.projeto.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginControle {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/login")
    public ModelAndView exibirLogin() {
        return new ModelAndView("administrativo/login");
    }

    @PostMapping("/areaLogada")
    ModelAndView login(
            @RequestParam("username") String username,//pega os parametros do 'form'
            @RequestParam("password") String password
    ) {
        Boolean ehUsuarioCorreto = usuarioRepositorio.existsByUsernameAndSenha(username, password);//faz o comparativo
        //de usuario e senha com o Boolean dentro do repositorio e segue o if/else
        if (ehUsuarioCorreto) {
            Usuario usuario = usuarioRepositorio.findByUsername(username);//cria o objeto 'usuario' de acordo com
            // o username (se 'marcos', será o objeto e tudo relacionado ao 'id' desse usuario)
            ModelAndView mv = new ModelAndView("administrativo/areaLogada");
            mv.addObject("usuario", usuario);//adiciona o objeto ao 'mv'
            return mv;
        }
        ModelAndView mv = new ModelAndView("administrativo/login");
        mv.addObject("mensagemDeErro", "Login Incorreto");
        return mv;
    }
}
