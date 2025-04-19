package com.example.emtlab.config;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Book;
import com.example.emtlab.model.domain.Country;
import com.example.emtlab.model.domain.User;
import com.example.emtlab.model.enumeration.BookCategory;
import com.example.emtlab.model.enumeration.Role;
import com.example.emtlab.repo.AuthorRepository;
import com.example.emtlab.repo.BookRepository;
import com.example.emtlab.repo.CountryRepository;
import com.example.emtlab.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BookRepository bookRepository, AuthorRepository authorRepository, CountryRepository countryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        Country mk = new Country("Mk", "E");
        Country a = new Country("Australia", "Australia");

        countryRepository.save(mk);
        countryRepository.save(a);

        Author a1 = new Author("Name1", "Surname1", mk);
        Author a2 = new Author("Second name", "Second surname", a);

        authorRepository.save(a1);
        authorRepository.save(a2);

        Book b1 = new Book("book1", BookCategory.BIOGRAPHY, a1, 10);
        Book b2 = new Book("book2", BookCategory.DRAMA, a2, 0);
        Book b3 = new Book("book3", BookCategory.NOVEL, a1, 1);

        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);

//        List<Book> listB = new ArrayList<>();
//        listB.add(b1);
//        listB.add(b2);
//
//        b3.setRelatedBooks(listB);
//        bookRepository.save(b3);

        User u1 = new User("user", passwordEncoder.encode("user"), "User name", "User surname", Role.ROLE_USER);
        u1.setBooksWishlist(List.of(b1, b2));

        userRepository.save(u1);
        userRepository.save(new User("lib", passwordEncoder.encode("lib"), "Librarian name", "Librarian surname", Role.ROLE_LIBRARIAN));

    }
}