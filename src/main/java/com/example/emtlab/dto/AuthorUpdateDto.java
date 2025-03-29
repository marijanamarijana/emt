package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Country;

public record AuthorUpdateDto(String name, String surname, Long country) {

    public Author toAuthor(Country country){
        return new Author(name, surname, country);
    }
}
