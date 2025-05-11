package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Book;
import com.example.emtlab.model.enumeration.BookCategory;

public record BookCreateDto (String name, BookCategory category, Long author, Integer availableCopies){
    public static BookCreateDto from(Book book) {
        return new BookCreateDto(
                book.getName(),
                book.getCategory(),
                book.getAuthor().getId(),
                book.getAvailableCopies()
        );
    }
    public Book toBook(Author author){
        return new Book(name, category, author, availableCopies);
    }
}
