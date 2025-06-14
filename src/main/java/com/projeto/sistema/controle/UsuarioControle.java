package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Perfil;
import com.projeto.sistema.modelo.Usuario;
import com.projeto.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UsuarioControle {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/cadastrarUsuario")
    public ModelAndView cadastrarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("administrativo/usuario/cadastraUsuario");
        mv.addObject("perfis", Perfil.values());
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("/salvarUsuario")
    public ModelAndView salvarUsuario(Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrarUsuario(usuario);
        }
        usuarioRepositorio.saveAndFlush(usuario);
        return new ModelAndView("redirect:/retornarLogin?mensagem=Usuário cadastrado com sucesso!");
    }

    @GetMapping("/listarUsuario/{perfil}")
    public ModelAndView listarUsuario(@PathVariable ("perfil") Perfil perfil) {
        ModelAndView mv = new ModelAndView("administrativo/usuario/listaUsuario");

        //Se o perfil não for 'USUARIO', redireciona para 'acessoNegado' (Criar página)
        if (!perfil.equals(Perfil.USUARIO)) {
            mv.setViewName("redirect:/acessoNegado");
            return mv;
        }

        mv.addObject("listaUsuario", usuarioRepositorio.findByPerfil(perfil));
        mv.addObject("usuarioPerfil", perfil);
        return mv;
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