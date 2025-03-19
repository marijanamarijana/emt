package com.example.emtlab.config;

import com.example.emtlab.model.Author;
import com.example.emtlab.model.Book;
import com.example.emtlab.model.Country;
import com.example.emtlab.model.enumeration.BookCategory;
import com.example.emtlab.repo.AuthorRepository;
import com.example.emtlab.repo.BookRepository;
import com.example.emtlab.repo.CountryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public DataInitializer(BookRepository bookRepository, AuthorRepository authorRepository, CountryRepository countryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }

    @PostConstruct
    public void init() {

        Country mk = new Country("Mk", "E");
        Country a = new Country("Australia", "Australia");

        countryRepository.save(mk);
        countryRepository.save(a);

        Author a1 = new Author("Name1", "Surname1", mk);
        Author a2 = new Author("Ime vtor avtor", "prezime vtor avtor", a);

        authorRepository.save(a1);
        authorRepository.save(a2);

        Book b1 = new Book("book1", BookCategory.BIOGRAPHY, a1, 10);
        Book b2 = new Book("book2", BookCategory.DRAMA, a2, 0);
        Book b3 = new Book("book3", BookCategory.NOVEL, a1, 1);

        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);

        List<Book> listB = new ArrayList<>();
        listB.add(b1);
        listB.add(b2);

        b3.setRelatedBooks(listB);
        bookRepository.save(b3);

    }
}