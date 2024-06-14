package com.example.storecheckoutsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    @JsonProperty("id_produto")
    private Integer idProduto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_precoProduto", nullable = false)
    @JsonProperty("id_precoProduto")
    private PrecoProduto precoProduto;


    @Column(name = "nome_produto", length = 100, nullable = false)
    @JsonProperty("nome_produto")
    private String nomeProduto;

    @Column(name = "descricao_produto", length = 255, nullable = false)
    @JsonProperty("descricao_produto")
    private String descricaoProduto;

    @Column(name = "quantidade_produto", nullable = false)
    @JsonProperty("quantidade_produto")
    private int quantidadeProduto;

    public Produto() {
        this.precoProduto = new PrecoProduto();
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public PrecoProduto getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(PrecoProduto precoProduto) {
        this.precoProduto = precoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }
}