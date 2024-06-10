package com.example.storecheckoutsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.storecheckoutsystem.model.Markup;
import com.example.storecheckoutsystem.repository.MarkupRepository;

@Service
public class MarkupService {

    Markup markup = new Markup();
    @Autowired
    private MarkupRepository markupRepository;

    public Iterable<Markup> getAllMarkups() {
        return markupRepository.findAll();
    }

    public Markup cadastrarNovoMarkup(Markup markup) {
        markup.setDespesaFixa(markup.getDespesaFixa());
        markup.setDespesaVariavel(markup.getDespesaVariavel());
        markup.setMargemLucro(markup.getMargemLucro());
        markup.setResultadoMarkup(markup.getResultadoMarkup());
        return markupRepository.save(markup);
    }

    public Markup getLastMarkup() {
        return markupRepository.findTopByOrderByIdMarkupDesc();
    }

    public double calculateProductPrice(Double productPrice, Markup markup) {
        return productPrice * markup.getResultadoMarkup();
    }

}