package com.example.emtlab.service.application;

import com.example.emtlab.dto.AuthorCreateDto;
import com.example.emtlab.dto.AuthorDisplayDto;
import com.example.emtlab.dto.AuthorUpdateDto;

import java.util.List;
import java.util.Optional;

public interface AuthorApplicationService {
    List<AuthorDisplayDto> findAll();
    Optional<AuthorDisplayDto> findById(Long id);
    Optional<AuthorDisplayDto> update(Long id, AuthorUpdateDto authorUpdateDto);
    Optional<AuthorDisplayDto> save(AuthorCreateDto authorCreateDto);
    void deleteById(Long id);
}
