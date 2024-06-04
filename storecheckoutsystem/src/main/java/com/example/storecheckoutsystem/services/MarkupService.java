package com.example.storecheckoutsystem.services;

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

    public double calculateProductPrice(double productPrice, Markup markup) {
        return productPrice * markup.getResultadoMarkup();
    }

}