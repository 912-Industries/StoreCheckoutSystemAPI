package com.example.storecheckoutsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "PrecoProduto")
public class PrecoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_precoProduto")
    @JsonProperty("id_precoProduto")
    private Integer idPrecoProduto;

    @Column(name = "precoCusto_precoProduto")
    @JsonProperty("precoCusto_precoProduto")
    private Double precoCustoProduto;

    @Column(name = "precoFinal_precoProduto")
    @JsonProperty("precoFinal_precoProduto")
    private Double precoFinalProduto;

    public PrecoProduto() {}

    // getters and setters
    public Integer getIdPrecoProduto() {
        return idPrecoProduto;
    }

    public void setIdPrecoProduto(Integer idPrecoProduto) {
        this.idPrecoProduto = idPrecoProduto;
    }

    public Double getPrecoCustoProduto() {
        return precoCustoProduto;
    }

    public void setPrecoCustoProduto(Double precoCustoProduto) {
        this.precoCustoProduto = precoCustoProduto;
    }

    public Double getPrecoFinalProduto() {
        return precoFinalProduto;
    }

    public void setPrecoFinalProduto(Double precoFinalProduto) {
        this.precoFinalProduto = precoFinalProduto;
    }
}