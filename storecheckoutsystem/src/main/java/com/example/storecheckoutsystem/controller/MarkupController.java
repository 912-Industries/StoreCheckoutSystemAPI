package com.example.storecheckoutsystem.controller;

import com.example.storecheckoutsystem.services.MarkupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.storecheckoutsystem.model.Markup;

@RestController
@RequestMapping("/api/markup")
public class MarkupController {
    private final MarkupService markupService;

    public MarkupController(MarkupService markupService) {
        this.markupService = markupService;
    }

    public ResponseEntity<Iterable<Markup>> pesquisaMarkup() {
        Iterable<Markup> markups = markupService.getAllMarkups();
        return new ResponseEntity<>(markups, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Markup> cadastroMarkup(@Validated @RequestBody Markup markup) {
        Markup newMarkup = markupService.cadastrarNovoMarkup(markup);
        return new ResponseEntity<>(newMarkup, HttpStatus.CREATED);
    }

    public ResponseEntity<Markup> getLastMarkup() {
        Markup lastMarkup = markupService.getLastMarkup();
        return new ResponseEntity<>(lastMarkup, HttpStatus.OK);
    }

    public ResponseEntity<Double> calculateProductPrice(double productPrice, ResponseEntity<Markup> lastMarkup2) {
        Markup lastMarkup = markupService.getLastMarkup();
        double totalPrice = markupService.calculateProductPrice(productPrice, lastMarkup);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }
}
