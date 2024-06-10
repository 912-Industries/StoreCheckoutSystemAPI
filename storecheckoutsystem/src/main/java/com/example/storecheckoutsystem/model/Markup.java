package com.example.storecheckoutsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Markup")
public class Markup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_markup")
    @JsonProperty("id_markup")
    private Integer idMarkup;

    @Column(name = "despesaFixa_markup")
    @JsonProperty("despesaFixa_markup")
    private int despesaFixa;

    @Column(name = "despesaVariaveis_markup")
    @JsonProperty("despesaVariaveis_markup")
    private int despesaVariavel;

    @Column(name = "margemLucro_markup")
    @JsonProperty("margemLucro_markup")
    private int margemLucro;

    @Column(name = "resultadoMarkup_markup")
    @JsonProperty("resultadoMarkup_markup")
    private float resultadoMarkup;

    public Integer getIdMarkup() {
        return idMarkup;
    }

    /**
     * @param idMarkup the idMarkup to set
     */
    public void setIdMarkup(Integer idMarkup) {
        this.idMarkup = idMarkup;
    }

    /**
     * @return int return the despesaFixa
     */
    public int getDespesaFixa() {
        return despesaFixa;
    }

    /**
     * @param despesaFixa the despesaFixa to set
     */
    public void setDespesaFixa(int despesaFixa) {
        this.despesaFixa = despesaFixa;
    }

    /**
     * @return int return the despesaVariavel
     */
    public int getDespesaVariavel() {
        return despesaVariavel;
    }

    /**
     * @param despesaVariavel the despesaVariavel to set
     */
    public void setDespesaVariavel(int despesaVariavel) {
        this.despesaVariavel = despesaVariavel;
    }

    /**
     * @return int return the margemLucro
     */
    public int getMargemLucro() {
        return margemLucro;
    }

    /**
     * @param margemLucro the margemLucro to set
     */
    public void setMargemLucro(int margemLucro) {
        this.margemLucro = margemLucro;
    }

    /**
     * @return float return the resultadoMarkup
     */
    public float getResultadoMarkup() {
        return resultadoMarkup;
    }

    /**
     * @param resultadoMarkup the resultadoMarkup to set
     */
    public void setResultadoMarkup(float resultadoMarkup) {
        this.resultadoMarkup = resultadoMarkup;
    }

}
