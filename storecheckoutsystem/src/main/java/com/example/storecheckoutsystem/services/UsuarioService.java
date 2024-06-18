package com.example.storecheckoutsystem.services;

import com.example.storecheckoutsystem.model.Usuario;
import com.example.storecheckoutsystem.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(usuario.getSenha_usuario());

        usuario.setNomeUsuario(usuario.getNome_usuario());
        usuario.setEmail_usuario(usuario.getEmail_usuario());
        usuario.setSenha_usuario(hashedPassword); // Atualize a senha criptografada

        System.out.println(hashedPassword);
        return usuarioRepository.save(usuario);
    }

    public Usuario pesquisaUsuarioPorId(int id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        return optionalUsuario.orElse(null);
    }

    public String usuarioLogin(Usuario login) {
        Optional<Usuario> usuario = usuarioRepository.findByNomeUsuario(login.getNome_usuario());

        if (usuario.isPresent()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(login.getSenha_usuario(), usuario.get().getSenha_usuario())) {
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
        usuarioAtual.setSenha_usuario(usuario.getSenha_usuario());
        return usuarioRepository.save(usuarioAtual);
    }
    public void excluirProduto(int id) {
        usuarioRepository.deleteById(id);
    }

}
