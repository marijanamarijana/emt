package com.example.emtlab.model.dto;

import com.example.emtlab.model.enumeration.BookCategory;
import jdk.jfr.Category;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class BookRelatedDto {

    private Long id;
    private String name;
    private BookCategory category;
    private Long authorId;
    private Integer availableCopies;
    private List<Long> relatedBookIds;

    public BookRelatedDto(Long id, String name, BookCategory category, Long authorId, Integer availableCopies, List<Long> relatedBookIds) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.authorId = authorId;
        this.availableCopies = availableCopies;
        this.relatedBookIds = relatedBookIds;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BookCategory getCategory() {
        return category;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public List<Long> getRelatedBookIds() {
        return relatedBookIds;
    }
}
