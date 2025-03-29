package com.example.emtlab.model.domain;

import com.example.emtlab.model.enumeration.BookCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @ManyToOne
    private Author author;

    private Integer availableCopies;

    @ManyToMany
    private List<Book> relatedBooks;

    @ManyToMany(mappedBy = "booksWishlist")
    private List<User> usersWhoWishlisted;

    public Book() {
    }
    public Book(String name, BookCategory category, Author author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }

}
