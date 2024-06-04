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
    
    public ResponseEntity<Double> calculateProductPrice(double productPrice) {
        Markup lastMarkup = markupService.getLastMarkup();
        double totalPrice = markupService.calculateProductPrice(productPrice, lastMarkup);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }
}
