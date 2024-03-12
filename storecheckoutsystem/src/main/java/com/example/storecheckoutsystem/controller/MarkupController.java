package com.example.storecheckoutsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.storecheckoutsystem.model.Markup;
import com.example.storecheckoutsystem.repository.MarkupRepository;

@RestController
@RequestMapping("/api/markup")
public class MarkupController {
    Markup markup = new Markup();
    @Autowired
    private MarkupRepository markupRepository;

    @GetMapping
    public ResponseEntity<Iterable<Markup>> pesquisaMarkup() {
        Iterable<Markup> markups = markupRepository.findAll();
        return new ResponseEntity<>(markups, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public Markup cadastroMarkup(@Validated @RequestBody Markup markup) {
        markup.setDespesaFixa(markup.getDespesaFixa());
        markup.setDespesaVariavel(markup.getDespesaVariavel());
        markup.setMargemLucro(markup.getMargemLucro());
        markup.setResultadoMarkup(markup.getResultadoMarkup());
        return markupRepository.save(markup);
    }

    
    public Markup getLastMarkup() {
        return markupRepository.findTopByOrderByIdMarkupDesc();
    }
    
    public double calculateProductPrice(double productPrice, Markup markup) {
        return productPrice * markup.getResultadoMarkup();
    }
}
