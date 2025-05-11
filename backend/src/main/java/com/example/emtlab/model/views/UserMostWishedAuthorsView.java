package com.example.emtlab.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM public.most_wished_authors")
@Immutable
public class UserMostWishedAuthorsView {

    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "author_id")
    private Long authorId;

    private String name;
    private String surname;

    @Column(name = "counted_wished_author_books")
    private Integer countedWishedAuthorBooks;
}
