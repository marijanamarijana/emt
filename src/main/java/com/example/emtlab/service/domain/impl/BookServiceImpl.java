package com.example.emtlab.service.domain.impl;

import com.example.emtlab.model.domain.Book;
import com.example.emtlab.dto.BookRelatedDto;
import com.example.emtlab.model.views.BooksPerAuthorView;
import com.example.emtlab.repo.BookRepository;
import com.example.emtlab.repo.BooksPerAuthorViewRepository;
import com.example.emtlab.service.domain.AuthorService;
import com.example.emtlab.service.domain.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, BooksPerAuthorViewRepository booksPerAuthorViewRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (book.getName() != null) {
                        existingBook.setName(book.getName());
                    }
                    if (book.getCategory() != null) {
                        existingBook.setCategory(book.getCategory());
                    }
                    if (book.getAvailableCopies() != null) {
                        existingBook.setAvailableCopies(book.getAvailableCopies());
                    }
                    if (book.getAuthor() != null && authorService.findById(book.getAuthor().getId()).isPresent()) {
                        existingBook.setAuthor(authorService.findById(book.getAuthor().getId()).get());
                    }
                    return bookRepository.save(existingBook);
                });

    }

    @Override
    public Optional<Book> save(Book book) {
        if (book.getAuthor() != null &&
                authorService.findById(book.getAuthor().getId()).isPresent()) {
            return Optional.of(
                    bookRepository.save(new Book(book.getName(), book.getCategory(),
                            authorService.findById(book.getAuthor().getId()).get(), book.getAvailableCopies())));
        }
        return Optional.empty();

    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }


    // nez dali ova se bara iskreno
    @Override
    public Optional<Book> markRented(Long id) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    if (existingBook.getAvailableCopies() > 0){
                        existingBook.setAvailableCopies(existingBook.getAvailableCopies() - 1);
                        return bookRepository.save(existingBook);
                    }
                    return existingBook;
                });
    }

    @Override
    @Transactional
    public List<BookRelatedDto> getRelated(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(""));


        return book.getRelatedBooks().stream()
                .map(b -> new BookRelatedDto(
                        b.getId(),
                        b.getName(),
                        b.getCategory(),
                        (b.getAuthor() != null) ? b.getAuthor().getId() : null,
                        b.getAvailableCopies(),
                        (b.getRelatedBooks() != null) ?
                                b.getRelatedBooks().stream().map(Book::getId).collect(Collectors.toList())
                                : new ArrayList<>()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public void refreshMaterializedView() {
        booksPerAuthorViewRepository.refreshMaterializedView();
    }

    @Override
    public List<BooksPerAuthorView> getBooksPerAuthorStats() {
        return booksPerAuthorViewRepository.findAll();
    }
}
