package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Book;
import com.example.emtlab.model.enumeration.BookCategory;

public record BookUpdateDto(String name, BookCategory category, Long author, Integer availableCopies) {
    public static BookDisplayDto from(Book book) {
        return new BookDisplayDto(
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
