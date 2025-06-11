package com.projeto.sistema.seguranca;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessaoFiltro implements Filter {

    //Excessões que não dependem de um Usuário logado
    private static final String[] URLS_LIVRES = {
            "/login",
            "/cadastrarUsuario",
            "/trocarSenha",
            "/areaLogada",
            "/acessoNegado"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String caminho = req.getRequestURI();
        HttpSession session = req.getSession(false); //false = não cria nova sessão

        //Libera recursos estáticos (js, css, imagens, fontes)
        if (caminho.startsWith("/css") ||
                caminho.startsWith("/js") ||
                caminho.startsWith("/images") ||
                caminho.startsWith("/webjars") ||
                caminho.endsWith(".css") ||
                caminho.endsWith(".js") ||
                caminho.endsWith(".png") ||
                caminho.endsWith(".jpg") ||
                caminho.endsWith(".jpeg") ||
                caminho.endsWith(".svg") ||
                caminho.endsWith(".woff2") ||
                caminho.endsWith(".woff") ||
                caminho.endsWith(".ttf")) {
            chain.doFilter(request, response);
            return;
        }
        //Verifica se a URL é livre (não precisa de login)
        boolean urlLivre = false;
        for (String url : URLS_LIVRES) {
            if (caminho.startsWith(url)) {
                urlLivre = true;
                break;
            }
        }
        //Se não for livre e o usuário não estiver logado, redireciona para login
        if (!urlLivre && (session == null || session.getAttribute("usuarioLogado") == null)) {
            resp.sendRedirect("/login");
            return;
        }
        // Continua normalmente
        chain.doFilter(request, response);
    }
}