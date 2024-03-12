package com.example.storecheckoutsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.storecheckoutsystem.model.Markup;

@Repository
public interface MarkupRepository extends JpaRepository<Markup, Integer> {
    Markup findTopByOrderByIdMarkupDesc();
}
