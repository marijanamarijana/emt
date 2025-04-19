package com.example.emtlab.service.domain;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.projections.AuthorProjection;
import com.example.emtlab.model.views.AuthorsPerCountryView;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Optional<Author> update(Long id, Author author);
    Optional<Author> save(Author author);
    void deleteById(Long id);
    void refreshMaterializedView();
    List<AuthorsPerCountryView> getAuthorsByCountry();
    List<AuthorProjection> takeNameAndSurnameByProjection();
}
