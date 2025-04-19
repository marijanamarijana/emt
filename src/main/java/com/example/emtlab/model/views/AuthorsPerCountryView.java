package com.example.emtlab.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM public.authors_by_country")
@Immutable
public class AuthorsPerCountryView {
    @Id
    @Column(name = "country_id")
    private Long country_id;

    @Column(name = "num_authors")
    private Integer numAuthors;
}
