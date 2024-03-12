package com.example.storecheckoutsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.storecheckoutsystem.services.AutoCompleteService;

@Controller
public class AutoCompleteController {
    @Autowired
    private AutoCompleteService autoCompleteService;

    @RequestMapping(value = "/api/autocomplete", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> autocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return autoCompleteService.getSuggestions(term);
    }
}
