package com.example.emtlab.service.domain;

import com.example.emtlab.model.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Optional<Author> update(Long id, Author author);
    Optional<Author> save(Author author);
    void deleteById(Long id);
}
