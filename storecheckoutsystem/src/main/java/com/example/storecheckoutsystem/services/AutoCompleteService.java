package com.example.storecheckoutsystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.storecheckoutsystem.repository.AutoCompleteRepository;

@Service
public class AutoCompleteService {
    @Autowired
    private AutoCompleteRepository autoCompleteRepository;

    public List<Object[]> getSuggestions(String term) {
        return autoCompleteRepository.findSuggestions(term);
    }
}