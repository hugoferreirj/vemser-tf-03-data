package br.com.dbc.vemser.walletlife.controllers;

import br.com.dbc.vemser.walletlife.doc.UsuarioControllerDoc;
import br.com.dbc.vemser.walletlife.dto.*;
import br.com.dbc.vemser.walletlife.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
@Validated
@Slf4j
public class UsuarioController implements UsuarioControllerDoc {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar(){
        log.info("Usuário: listar todos");
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> listarPessoasPorId(@PathVariable("idUsuario") @Positive Integer idUsuario){
        log.info("Usuário: listar por Id do usuário");
        return new ResponseEntity<>(usuarioService.listarPessoasPorId(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/usuario-despesa")
    public ResponseEntity<Set<UsuarioComDespesaDTO>> findAllUsuariosDespesa(){
        return new ResponseEntity<>(usuarioService.findAllUsuariosDespesa(), HttpStatus.OK);
    }

    @GetMapping("/usuario-receita")
    public ResponseEntity<Set<UsuarioComReceitaDTO>> findallUsuarioReceita(
            @RequestParam(value = "valor", required = false) Double valor){
        return new ResponseEntity<>(usuarioService.findallUsuarioReceita(valor), HttpStatus.OK);
    }

    @GetMapping("/usuario-investimento")
    public ResponseEntity<Set<UsuarioComInvestimentoDTO>> findUsuariosByInvestimentoCorretora(
            @RequestParam(value = "corretora", required = false) String corretora){
        return new ResponseEntity<>(usuarioService.findUsuariosByInvestimentoCorretora(corretora), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> adicionarUsuario(@RequestBody @Valid UsuarioCreateDTO usuario){
        log.info("Usuário: inserir novo");
        return new ResponseEntity<>(usuarioService.adicionarUsuario(usuario), HttpStatus.OK);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> editarPessoa(@PathVariable @Positive Integer idUsuario,
                                                   @RequestBody @Valid UsuarioCreateDTO usuario){
        log.info("Usuário: editar");
        UsuarioDTO usuarioAtualizado = usuarioService.editarPessoa(idUsuario, usuario);
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> remover(@PathVariable Integer idUsuario){
        log.info("Usuário: deletar por id");
        usuarioService.removerPessoa(idUsuario);
        return ResponseEntity.ok().build();
    }

}
