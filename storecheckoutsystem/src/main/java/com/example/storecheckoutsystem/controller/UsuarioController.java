package com.example.storecheckoutsystem.controller;

import com.example.storecheckoutsystem.model.Usuario;
import com.example.storecheckoutsystem.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public Iterable<Usuario> pesquisaUsuario() {
        return usuarioService.pesquisaUsuarios();
    }

    @PostMapping("/cadastro-usuario")
    public ResponseEntity<Usuario> cadastroUsuario(@Validated @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastroUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id_usuario}")
    public ResponseEntity<Usuario> pesquisaUsuarioId(@PathVariable int id_usuario) {
        Usuario usuario = usuarioService.pesquisaUsuarioPorId(id_usuario);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.editarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable int id) {
        usuarioService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@Validated @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> usuarioLogin(@Validated @RequestBody Usuario login) {
        String mensagem = usuarioService.usuarioLogin(login);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }
}
