package com.example.storecheckoutsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.storecheckoutsystem.model.Produto;

public interface AutoCompleteRepository extends JpaRepository<Produto, Integer> {

    @Query(value = "SELECT nome_produto, id_produto, categoria_produto FROM produto WHERE " +
            "MATCH(nome_produto, categoria_produto) AGAINST(:term IN NATURAL LANGUAGE MODE) LIMIT 10", nativeQuery = true)
    List<Object[]> findSuggestions(String term);
}