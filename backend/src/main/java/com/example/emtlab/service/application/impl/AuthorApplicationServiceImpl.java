package com.example.emtlab.service.application.impl;

import com.example.emtlab.dto.AuthorDisplayDto;
import com.example.emtlab.dto.AuthorUpdateDto;
import com.example.emtlab.model.domain.Country;
import com.example.emtlab.model.projections.AuthorProjection;
import com.example.emtlab.model.views.AuthorsPerCountryView;
import com.example.emtlab.service.application.AuthorApplicationService;
import com.example.emtlab.service.domain.AuthorService;
import com.example.emtlab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {

    private final AuthorService authorService;
    private final CountryService countryService;

    public AuthorApplicationServiceImpl(AuthorService authorService, CountryService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @Override
    public List<AuthorDisplayDto> findAll() {
        return authorService.findAll().stream().map(AuthorDisplayDto::from).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDisplayDto> findById(Long id) {
        return authorService.findById(id).map(AuthorDisplayDto::from);
    }

    @Override
    public Optional<AuthorDisplayDto> update(Long id, AuthorUpdateDto authorUpdateDto) {
        Optional<Country> country = countryService.findById(authorUpdateDto.countryId());

        return authorService.update(id,
                        authorUpdateDto.toAuthor(
                                country.orElse(null)
                        )
                )
                .map(AuthorDisplayDto::from);
    }

    @Override
    public Optional<AuthorDisplayDto> save(AuthorUpdateDto authorDto) {
        Optional<Country> country = countryService.findById(authorDto.countryId());

        if (country.isPresent()) {
            return authorService.save(authorDto.toAuthor(country.get()))
                    .map(AuthorDisplayDto::from);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        authorService.deleteById(id);
    }

    @Override
    public List<AuthorsPerCountryView> getAuthorsByCountry() {
        return authorService.getAuthorsByCountry();
    }
    @Override
    public List<AuthorProjection> takeNameAndSurnameByProjection(){
        return authorService.takeNameAndSurnameByProjection();
    }
}
