package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Usuario;
import com.projeto.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class UsuarioControle {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/cadastrarUsuario")
    public ModelAndView cadastrarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("administrativo/usuario/cadastraUsuario");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("/salvarUsuario")
    public ModelAndView salvarUsuario(Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrarUsuario(usuario);
        }
        usuarioRepositorio.saveAndFlush(usuario);
        String mensagem = "Usuário Salvo com sucesso!";
        return retornarLogin(mensagem);
    }

    @GetMapping("/editarUsuario/{id}")
    public ModelAndView editarUsuario(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        return cadastrarUsuario(usuario.get());
    }

    @GetMapping("/retornarLogin")
    public ModelAndView retornarLogin(String mensagem){
        ModelAndView mv = new ModelAndView("administrativo/login");
        mv.addObject("mensagem", mensagem);
        return mv;
    }

    @GetMapping("/removerUsuario/{id}")
    public ModelAndView deletarUsuario(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        usuarioRepositorio.delete(usuario.get());
        String mensagem = "Usuário deletado com sucesso!";
        return retornarLogin(mensagem);
    }
}
//        mv.addObject("mensagemDeCadastro", "Usuário cadastrado com sucesso!");
//        mv.addObject("mensagemDeEdicao", "Usuário editado com sucesso!");
//        mv.addObject("mensagemDeExclusao", "Usuário deletado com sucesso!");