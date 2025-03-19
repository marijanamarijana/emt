package com.example.emtlab.service;

import com.example.emtlab.model.Book;
import com.example.emtlab.model.dto.BookDto;
import com.example.emtlab.model.dto.BookRelatedDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> update(Long id, BookDto book);
    Optional<Book> save(BookDto book);
    void deleteById(Long id);
    Optional<Book> markRented(Long id);
    List<BookRelatedDto> getRelated(Long id);
}
