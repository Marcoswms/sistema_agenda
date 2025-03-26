package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Cliente;
import com.projeto.sistema.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class ClienteControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/cadastrarCliente")
    public ModelAndView cadastrar(Cliente cliente) {
        ModelAndView mv = new ModelAndView("administrativo/cliente/cadastro_cliente");
        mv.addObject("cliente", cliente);
        return mv;
    }
    @PostMapping("/salvarCliente")
    public ModelAndView salvar(Cliente cliente, BindingResult result) {
        if(result.hasErrors()){
            return cadastrar(cliente);
        }
        clienteRepositorio.saveAndFlush(cliente);
        return cadastrar(new Cliente());
    }
    @GetMapping("/listarCliente")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/cliente/lista");
        mv.addObject("listaCliente", clienteRepositorio.findAll());
        return mv;
    }
    @GetMapping("/editarCliente/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> estado = clienteRepositorio.findById(id);
        return cadastrar(estado.get());
    }
    @GetMapping("/removerCliente/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cliente> estado = clienteRepositorio.findById(id);
        clienteRepositorio.delete(estado.get());
        return listar();
    }
}