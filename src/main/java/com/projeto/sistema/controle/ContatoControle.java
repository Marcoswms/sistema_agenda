package com.projeto.sistema.controle;

import com.projeto.sistema.modelo.Cliente;
import com.projeto.sistema.modelo.Contato;
import com.projeto.sistema.repositorio.ClienteRepositorio;
import com.projeto.sistema.repositorio.ContatoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatoControle {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ContatoRepositorio contatoRepositorio;

    @GetMapping("/cadastrarContato/{clienteId}")
    public ModelAndView cadastrar(Contato contato, @PathVariable("clienteId") Long clienteId) {
        ModelAndView mv = new ModelAndView("administrativo/contato/cadastraContato");
        mv.addObject("contato", contato);
        mv.addObject("clienteId", clienteId);
        return mv;
    }

    //@RequestParam: pega o parametro que foi passado dentro do 'form' através do 'nome:' e 'value'
    @PostMapping("/salvarContato")
    public ModelAndView salvar(@ModelAttribute("contato") Contato contato,
                               @RequestParam("clienteId") Long clienteId,
                               BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(contato, clienteId);
        }
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        contato.setCliente(cliente);

        contatoRepositorio.saveAndFlush(contato);
        return cadastrar(new Contato(), clienteId);
    }
//    @GetMapping("/editarContato/{id}")
//    public ModelAndView editar(@PathVariable("id") Long id) {
//        Optional<Contato> contato = contatoRepositorio.findById(id);
//        return cadastrar(contato.get());
//    }

    @GetMapping("/listarContato/{usuarioId}")//puxar o id do usuário para filtrar findById(id)
    public ModelAndView listar(@PathVariable("usuarioId") Long usuarioId) {
        ModelAndView mv = new ModelAndView("administrativo/contato/listaContato");
        mv.addObject("listaContato", contatoRepositorio.findByCliente_UsuarioId(usuarioId));
        mv.addObject("usuarioId", usuarioId);
        return mv;
    }

//    @GetMapping("/removerContato/{id}")
//    public ModelAndView deletar(@PathVariable("id") Long id) {
//        Optional<Contato> contato = contatoRepositorio.findById(id);
//        contatoRepositorio.delete(contato.get());
//        return listar();
//    }
}