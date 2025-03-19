package com.example.emtlab.model.dto;

import com.example.emtlab.model.Author;
import com.example.emtlab.model.enumeration.BookCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class BookDto {
    private String name;

    private BookCategory category;

    private Long author;

    private Integer availableCopies;

    public BookDto() {
    }
    public BookDto(String name, BookCategory category, Long author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public String getName() {
        return name;
    }

    public BookCategory getCategory() {
        return category;
    }

    public Long getAuthor() {
        return author;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }
}
