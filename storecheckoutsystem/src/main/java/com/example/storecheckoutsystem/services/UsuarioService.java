package com.example.storecheckoutsystem.services;

import com.example.storecheckoutsystem.model.Usuario;
import com.example.storecheckoutsystem.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Iterable<Usuario> pesquisaUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario cadastroUsuario(Usuario usuario) {
        usuario.setNomeUsuario(usuario.getNome_usuario());
        usuario.setEmail_usuario(usuario.getEmail_usuario());
        usuario.setSenha_usuario(usuario.getSenha_usuario());
        return usuarioRepository.save(usuario);
    }

    public Usuario pesquisaUsuarioPorId(int id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.orElse(null);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public String usuarioLogin(Usuario login) {
        Optional<Usuario> usuario = usuarioRepository.findByNomeUsuario(login.getNome_usuario());
        if (usuario.isPresent()) {
            if (login.getSenha_usuario().equals(usuario.get().getSenha_usuario())) {
                return "Login feito com sucesso";
            } else {
                return "Senha Inválida";
            }
        } else {
            return "Usuário não encontrado";
        }
    }


    public Usuario editarUsuario(int id, Usuario usuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuarioAtual = optionalUsuario.get();
        usuarioAtual.setNomeUsuario(usuario.getNome_usuario());
        usuarioAtual.setEmail_usuario(usuario.getEmail_usuario());
        usuarioAtual.setNivel_permissao_usuario(usuario.getNivel_permissao_usuario());
        usuarioAtual.setSenha_usuario(usuario.getSenha_usuario());
        return usuarioRepository.save(usuarioAtual);
    }
    public void excluirProduto(int id) {
        usuarioRepository.deleteById(id);
    }
}
