package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Book;
import com.example.emtlab.model.enumeration.BookCategory;

import java.util.List;
import java.util.stream.Collectors;

public record BookDisplayDto (String name, BookCategory category, Long author, Integer availableCopies){
    public static BookDisplayDto from(Book book) {
        return new BookDisplayDto(
                book.getName(),
                book.getCategory(),
                book.getAuthor().getId(),
                book.getAvailableCopies()
        );
    }

    public static List<BookDisplayDto> from(List<Book> books) {
        return books.stream().map(BookDisplayDto::from).collect(Collectors.toList());
    }
}
