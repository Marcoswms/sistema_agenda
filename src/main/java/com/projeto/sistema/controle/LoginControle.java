package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Usuario;
import com.projeto.sistema.repositorio.UsuarioRepositorio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            @RequestParam("password") String password,
            HttpServletRequest request

    ) {
        Boolean ehUsuarioCorreto = usuarioRepositorio.existsByUsernameAndSenha(username, password);//faz o comparativo
        //de usuario e senha com o Boolean dentro do repositorio e segue o if/else
        if (ehUsuarioCorreto) {
            Usuario usuario = usuarioRepositorio.findByUsername(username);//'usuario' será o objeto de permanente em
            // 'areaLogada' e os cadastros serão relacionado ao 'id' desse usuario

            // Armazena o usuário na sessão
            request.getSession().setAttribute("usuarioLogado", usuario);

            ModelAndView mv = new ModelAndView("administrativo/areaLogada");
            mv.addObject("usuario", usuario);//adiciona o objeto ao 'mv'
            return mv;
        }
        ModelAndView mv = new ModelAndView("administrativo/login");
        mv.addObject("mensagemDeErro", "Login Incorreto");
        return mv;
    }

    @GetMapping("/inicio")
    public ModelAndView inicio(HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            // Usuário não logado, redireciona para login
            return new ModelAndView("redirect:/login");
        }
        // Usuário logado, carrega a área logada
        ModelAndView mv = new ModelAndView("administrativo/areaLogada");
        mv.addObject("usuario", usuarioLogado);
        return mv;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "redirect:/login";
    }

}