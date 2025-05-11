package com.example.emtlab.service.application;

import com.example.emtlab.dto.AuthorDisplayDto;
import com.example.emtlab.dto.AuthorUpdateDto;
import com.example.emtlab.model.projections.AuthorProjection;
import com.example.emtlab.model.views.AuthorsPerCountryView;

import java.util.List;
import java.util.Optional;

public interface AuthorApplicationService {
    List<AuthorDisplayDto> findAll();
    Optional<AuthorDisplayDto> findById(Long id);
    Optional<AuthorDisplayDto> update(Long id, AuthorUpdateDto authorUpdateDto);
    Optional<AuthorDisplayDto> save(AuthorUpdateDto authorCreateDto);
    void deleteById(Long id);
    List<AuthorsPerCountryView> getAuthorsByCountry();
    List<AuthorProjection> takeNameAndSurnameByProjection();
}
