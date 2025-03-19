package com.example.emtlab.service.impl;

import com.example.emtlab.model.Book;
import com.example.emtlab.model.dto.BookDto;
import com.example.emtlab.model.dto.BookRelatedDto;
import com.example.emtlab.repo.AuthorRepository;
import com.example.emtlab.repo.BookRepository;
import com.example.emtlab.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
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
    public Optional<Book> update(Long id, BookDto book) {
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
                    if (book.getAuthor() != null && authorRepository.findById(book.getAuthor()).isPresent()) {
                        existingBook.setAuthor(authorRepository.findById(book.getAuthor()).get());
                    }
                    return bookRepository.save(existingBook);
                });

    }

    @Override
    public Optional<Book> save(BookDto book) {
        if (book.getAuthor() != null &&
                authorRepository.findById(book.getAuthor()).isPresent()) {
            return Optional.of(
                    bookRepository.save(new Book(book.getName(), book.getCategory(),
                            authorRepository.findById(book.getAuthor()).get(), book.getAvailableCopies())));
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
                .map(b -> {
                    return new BookRelatedDto(
                            b.getId(),
                            b.getName(),
                            b.getCategory(),
                            (b.getAuthor() != null) ? b.getAuthor().getId() : null,
                            b.getAvailableCopies(),
                            (b.getRelatedBooks() != null) ?
                                    b.getRelatedBooks().stream().map(Book::getId).collect(Collectors.toList())
                                    : new ArrayList<Long>()
                    );
                })
                .collect(Collectors.toList());

    }



//    @Override
//    public List<BookRelatedDto> getRelated(Long bookId) {
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(() -> new RuntimeException("Book was not found!!"));
//
//        return book.getRelatedBooks().stream()
//                .map(this::convert)
//                .collect(Collectors.toList());
//    }
//    private BookRelatedDto convert(Book book) {
//        List<Long> related = book.getRelatedBooks().stream().map(Book::getId).collect(Collectors.toList());
//
//        return new BookRelatedDto(book.getId(), book.getName(), book.getCategory(), book.getAuthor().getId(), book.getAvailableCopies(), related);
//    }

}
