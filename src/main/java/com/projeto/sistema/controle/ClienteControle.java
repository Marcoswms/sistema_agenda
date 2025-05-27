package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Cliente;
import com.projeto.sistema.modelo.Usuario;
import com.projeto.sistema.repositorio.ClienteRepositorio;
import com.projeto.sistema.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/cadastrarCliente/{usuarioId}")//12850
    public ModelAndView cadastrar(Cliente cliente, @PathVariable("usuarioId") Long usuarioId) {
        ModelAndView mv = new ModelAndView("administrativo/cliente/cadastraCliente");
        mv.addObject("cliente", cliente);
        mv.addObject("usuarioId", usuarioId);
        return mv;
    }

    @PostMapping("/salvarCliente")
    public ModelAndView salvar(@ModelAttribute("cliente") Cliente cliente,
                               @RequestParam("usuarioId") Long usuarioId,
                               BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(cliente, usuarioId);
        }
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        cliente.setUsuario(usuario);

        clienteRepositorio.saveAndFlush(cliente);
        return cadastrar(new Cliente(), usuarioId);
    }

    @GetMapping("/editarCliente/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> clienteOptional = clienteRepositorio.findById(id);
        Cliente cliente = clienteOptional.get();
        Long usuarioId = cliente.getUsuario().getId();
        return cadastrar(cliente, usuarioId);
    }

    @GetMapping("/listarCliente/{usuarioId}")//puxar o id do usuário para filtrar findById(id)
    public ModelAndView listar(@PathVariable("usuarioId") Long usuarioId) {
        ModelAndView mv = new ModelAndView("administrativo/cliente/lista");
        mv.addObject("listaCliente",clienteRepositorio.findByUsuarioId(usuarioId));
        return mv;
    }

    @GetMapping("/removerCliente/{usuarioId}")
    public ModelAndView deletar(@PathVariable("usuarioId") Long id) {
        Optional<Cliente> clienteOptional = clienteRepositorio.findById(id);
        Cliente cliente = clienteOptional.get();
        Long usuarioId = cliente.getUsuario().getId();
        clienteRepositorio.delete(cliente);
        return listar(usuarioId);
    }
}