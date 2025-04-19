package com.example.emtlab.service.domain;

import com.example.emtlab.model.domain.Book;
import com.example.emtlab.dto.BookRelatedDto;
import com.example.emtlab.model.views.BooksPerAuthorView;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> update(Long id, Book book);
    Optional<Book> save(Book book);
    void deleteById(Long id);
    Optional<Book> markRented(Long id);
    List<BookRelatedDto> getRelated(Long id);
    void refreshMaterializedView();
    List<BooksPerAuthorView> getBooksPerAuthorStats();
}
