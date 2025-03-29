package com.example.emtlab.service.application.impl;

import com.example.emtlab.dto.BookCreateDto;
import com.example.emtlab.dto.BookDisplayDto;
import com.example.emtlab.dto.BookRelatedDto;
import com.example.emtlab.dto.BookUpdateDto;
import com.example.emtlab.model.domain.Author;
import com.example.emtlab.service.application.BookApplicationService;
import com.example.emtlab.service.domain.AuthorService;
import com.example.emtlab.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookApplicationServiceImpl(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public List<BookDisplayDto> findAll() {
        return bookService.findAll().stream().map(BookDisplayDto::from).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDisplayDto> findById(Long id) {
        return bookService.findById(id).map(BookDisplayDto::from);
    }

    @Override
    public Optional<BookDisplayDto> update(Long id, BookUpdateDto bookUpdateDto) {
        Optional<Author> author = authorService.findById(bookUpdateDto.author());

        return bookService.update(id,
                        bookUpdateDto.toBook(
                                author.orElse(null)
                        )
                )
                .map(BookDisplayDto::from);
    }

    @Override
    public Optional<BookDisplayDto> save(BookCreateDto bookCreateDto) {
        Optional<Author> author = authorService.findById(bookCreateDto.author());

        if (author.isPresent()) {
            return bookService.save(bookCreateDto.toBook(author.get()))
                    .map(BookDisplayDto::from);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        bookService.deleteById(id);
    }

    @Override
    public Optional<BookDisplayDto> markRented(Long id) {
        return bookService.markRented(id).map(BookDisplayDto::from);
    }

    @Override
    public List<BookRelatedDto> getRelated(Long id) {
        return bookService.getRelated(id);
    }
}
