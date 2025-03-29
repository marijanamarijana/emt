package com.example.emtlab.dto;

import com.example.emtlab.model.enumeration.BookCategory;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class BookDto {
    private String name;

    private BookCategory category;

    private Long author;

    private Integer availableCopies;
}
