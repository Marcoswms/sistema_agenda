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

import java.util.Optional;

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

        //Puxa um cliente para 'extrair' seu 'usuarioId'
        Cliente cliente = clienteRepositorio.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        mv.addObject("usuarioId", cliente.getUsuario().getId());

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

    @GetMapping("/editarContato/{contatoId}")
    public ModelAndView editar(@PathVariable("contatoId") Long contatoId) {
        Optional<Contato> contatoOptional = contatoRepositorio.findById(contatoId);
        Contato contato = contatoOptional.get();
        Long id = contato.getId();
        return cadastrar(contato, id);
    }

    @GetMapping("/listarContato/{usuarioId}")//puxar o id do usuário para filtrar findById(id)
    public ModelAndView listar(@PathVariable("usuarioId") Long usuarioId) {
        ModelAndView mv = new ModelAndView("administrativo/contato/listaContato");
        mv.addObject("listaContato", contatoRepositorio.findByCliente_UsuarioId(usuarioId));
        mv.addObject("usuarioId", usuarioId);
        return mv;
    }

    @GetMapping("/removerContato/{contatoId}")
    public ModelAndView deletar(@PathVariable("id") Long contatoId) {
        Optional<Contato> contatoOptional = contatoRepositorio.findById(contatoId);
        Contato contato = contatoOptional.get();
        Long usuarioId = contato.getCliente().getUsuario().getId();
        contatoRepositorio.delete(contato);
        return listar(usuarioId);
    }
}