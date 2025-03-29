package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Country;

public record AuthorDisplayDto (String name, String surname, Long country) {

    public static AuthorDisplayDto from(Author author) {
        return new AuthorDisplayDto(
                author.getName(),
                author.getSurname(),
                author.getCountry().getId()
        );
    }

    public Author toAuthor(Country country){
        return new Author(name, surname, country);
    }
}
