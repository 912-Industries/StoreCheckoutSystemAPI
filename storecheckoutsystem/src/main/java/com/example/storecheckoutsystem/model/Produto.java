package com.example.storecheckoutsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    @JsonProperty("id_produto")
    private Integer idProduto;

    @Column(name = "nome_produto")
    @JsonProperty("nome_produto")
    private String nomeProduto;

    @Column(name = "precoFinal_produto")
    @JsonProperty("precoFinal_produto")
    private float precoFinalProduto;

    @Column(name = "categoria_produto")
    @JsonProperty("categoria_produto")
    private String categoriaProduto;

    @Column(name = "descricao_produto")
    @JsonProperty("descricao_produto")
    private String descricaoProduto;

    @Column(name = "precoCusto_produto")
    @JsonProperty("precoCusto_produto")
    private float precoCustoProduto;

    @Column(name = "quantidade_produto")
    @JsonProperty("quantidade_produto")
    private int quantidadeProduto;

    /**
     * @return Integer return the idProduto
     */
    public Integer getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * @return String return the nomeProduto
     */
    public String getNomeProduto() {
        return nomeProduto;
    }

    /**
     * @param nomeProduto the nomeProduto to set
     */
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    /**
     * @return float return the precoFinalProduto
     */
    public float getPrecoFinalProduto() {
        return precoFinalProduto;
    }

    /**
     * @param precoFinalProduto the precoFinalProduto to set
     */
    public void setPrecoFinalProduto(float precoFinalProduto) {
        this.precoFinalProduto = precoFinalProduto;
    }

    /**
     * @return String return the categoriaProduto
     */
    public String getCategoriaProduto() {
        return categoriaProduto;
    }

    /**
     * @param categoriaProduto the categoriaProduto to set
     */
    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    /**
     * @return String return the descricaoProduto
     */
    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    /**
     * @param descricaoProduto the descricaoProduto to set
     */
    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    /**
     * @return float return the precoCustoProduto
     */
    public float getPrecoCustoProduto() {
        return precoCustoProduto;
    }

    /**
     * @param precoCustoProduto the precoCustoProduto to set
     */
    public void setPrecoCustoProduto(float precoCustoProduto) {
        this.precoCustoProduto = precoCustoProduto;
    }

    /**
     * @return int return the quantidadeProduto
     */
    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    /**
     * @param quantidadeProduto the quantidadeProduto to set
     */
    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

}
