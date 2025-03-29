package com.example.emtlab.service.application;

import com.example.emtlab.dto.BookCreateDto;
import com.example.emtlab.dto.BookDisplayDto;
import com.example.emtlab.dto.BookRelatedDto;
import com.example.emtlab.dto.BookUpdateDto;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {

    List<BookDisplayDto> findAll();
    Optional<BookDisplayDto> findById(Long id);
    Optional<BookDisplayDto> update(Long id, BookUpdateDto bookCreateDto);
    Optional<BookDisplayDto> save(BookCreateDto bookCreateDto);
    void deleteById(Long id);
    Optional<BookDisplayDto> markRented(Long id);

    List<BookRelatedDto> getRelated(Long id);
}
