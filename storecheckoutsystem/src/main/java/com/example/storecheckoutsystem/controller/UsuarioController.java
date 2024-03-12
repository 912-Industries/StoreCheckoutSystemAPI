package com.example.storecheckoutsystem.controller;

import com.example.storecheckoutsystem.model.Usuario;
import com.example.storecheckoutsystem.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

  private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

  @Autowired
  private UsuarioRepository usuarioRepository;

  @GetMapping
  public Iterable<Usuario> pesquisaUsuario() {
    return usuarioRepository.findAll();
  }

  @PostMapping("/cadastro-usuario")
  public Usuario cadastroUsuario(@Validated @RequestBody Usuario usuario) {
    usuario.setNomeUsuario(usuario.getNome_usuario());
    usuario.setEmail_usuario(usuario.getEmail_usuario());
    usuario.setSenha_usuario(usuario.getSenha_usuario());
    return usuarioRepository.save(usuario);
  }

  @GetMapping("/{id_usuario}")
  public ResponseEntity<Usuario> pesquisaUsuarioId(
      @PathVariable(value = "id_usuario") int id_usuario) {
    Optional<Usuario> usuario = usuarioRepository.findById((int) id_usuario);
    if (usuario.isPresent()) {
      return ResponseEntity.ok().body(usuario.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public Usuario salvarUsuario(@Validated @RequestBody Usuario usuario) {
    return usuarioRepository.save(usuario);
  }

  @PostMapping("/login")
  public ResponseEntity<?> usuarioLogin(@Validated @RequestBody Usuario login) {
    logger.info("Received login request for user: {}", login.getNome_usuario());
    Optional<Usuario> usuariolog = usuarioRepository.findByNomeUsuario(login.getNome_usuario());
    logger.info("Found user in database: {}", usuariolog);

    Optional<Usuario> usuario = usuarioRepository.findByNomeUsuario(login.getNome_usuario());
    if (usuario.isPresent()) {
      if (login.getSenha_usuario().equals(usuario.get().getSenha_usuario())) {
        return ResponseEntity.ok().body("Login feito com sucesso");
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha Inválida");
      }
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    }
  }

}
