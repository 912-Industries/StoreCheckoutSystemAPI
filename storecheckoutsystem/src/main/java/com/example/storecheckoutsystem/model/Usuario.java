package com.example.storecheckoutsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  @JsonProperty("id_usuario")
  private int id_usuario;

  @Column(name = "nome_usuario",length = 100)
  @JsonProperty("nome_usuario")
  private String nomeUsuario;

  @Column(name = "email_usuario", length = 20, unique = true)
  @JsonProperty("email_usuario")
  private String email_usuario;

  @Column(name = "senha_usuario", length = 255)
  @JsonProperty("senha_usuario")
  private String senha_usuario;



  private Integer nivel_permissao_usuario;

  public int getId_usuario() {
    return id_usuario;
  }

  public String getNome_usuario() {
    return nomeUsuario;
  }

  public String getEmail_usuario() {
    return email_usuario;
  }

  public String getSenha_usuario() {
    return senha_usuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public void setEmail_usuario(String email_usuario) {
    this.email_usuario = email_usuario;
  }

  public void setSenha_usuario(String senha_usuario) {
    this.senha_usuario = senha_usuario;
  }


}
