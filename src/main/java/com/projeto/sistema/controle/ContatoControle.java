package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Cliente;
import com.projeto.sistema.modelo.Contato;
import com.projeto.sistema.repositorio.ClienteRepositorio;
import com.projeto.sistema.repositorio.ContatoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class ContatoControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ContatoRepositorio contatoRepositorio;

    @GetMapping("/cadastrarContato")
    public ModelAndView cadastrar(Contato contato) {
        ModelAndView mv = new ModelAndView("administrativo/contato/cadastraContato");
        mv.addObject("contato", contato);
        mv.addObject("listaCliente", clienteRepositorio.findAll());
        return mv;
    }
    @PostMapping("/salvarContato")
    public ModelAndView salvar(Contato contato, BindingResult result) {
        if(result.hasErrors()){
            return cadastrar(contato);
        }
        contatoRepositorio.saveAndFlush(contato);
        return cadastrar(new Contato());
    }
    @GetMapping("/listarContato")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/contato/listaContato");
        mv.addObject("listarContato", contatoRepositorio.findAll());
        return mv;
    }
    @GetMapping("/editarContato/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Contato> contato = contatoRepositorio.findById(id);
        return cadastrar(contato.get());
    }
    @GetMapping("/removerContato/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Contato> contato = contatoRepositorio.findById(id);
        contatoRepositorio.delete(contato.get());
        return listar();
    }
}