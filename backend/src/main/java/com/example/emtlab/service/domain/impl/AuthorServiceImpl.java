package com.example.emtlab.service.domain.impl;

import com.example.emtlab.events.AuthorCreatedEvent;
import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.projections.AuthorProjection;
import com.example.emtlab.model.views.AuthorsPerCountryView;
import com.example.emtlab.repo.AuthorRepository;
import com.example.emtlab.repo.AuthorsPerCountryViewRepository;
import com.example.emtlab.service.domain.AuthorService;
import com.example.emtlab.service.domain.CountryService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryService countryService;
    private final AuthorsPerCountryViewRepository authorsPerCountryViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryService countryService, AuthorsPerCountryViewRepository authorsPerCountryViewRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.authorRepository = authorRepository;
        this.countryService = countryService;
        this.authorsPerCountryViewRepository = authorsPerCountryViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> update(Long id, Author author) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    if (author.getName() != null) {
                        existingAuthor.setName(author.getName());
                    }
                    if (author.getSurname() != null) {
                        existingAuthor.setSurname(author.getSurname());
                    }
                    if (author.getCountry() != null && countryService.findById(author.getCountry().getId()).isPresent()) {
                        existingAuthor.setCountry(countryService.findById(author.getCountry().getId()).get());
                    }
                    Author a = authorRepository.save(existingAuthor);
                    this.applicationEventPublisher.publishEvent(new AuthorCreatedEvent(author));
                    return a;
                });

    }

    @Override
    public Optional<Author> save(Author author) {
        Optional<Author> savedAuthor = Optional.empty();

        if (author.getCountry() != null &&
                countryService.findById(author.getCountry().getId()).isPresent()) {
            savedAuthor = Optional.of(
                    authorRepository.save(new Author(
                           author.getName(), author.getSurname(),
                            countryService.findById(author.getCountry().getId()).get()
                    )));

            this.applicationEventPublisher.publishEvent(new AuthorCreatedEvent(author));
            return savedAuthor;
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Author> a = authorRepository.findById(id);
        authorRepository.deleteById(id);
//        if (a != null) this.applicationEventPublisher.publishEvent(new AuthorCreatedEvent(a));
    }

    @Override
    public void refreshMaterializedView() {
        authorsPerCountryViewRepository.refreshMaterializedView();
    }

    @Override
    public List<AuthorsPerCountryView> getAuthorsByCountry() {
        return authorsPerCountryViewRepository.findAll();
    }

    @Override
    public  List<AuthorProjection> takeNameAndSurnameByProjection(){
        return authorRepository.takeNameAndSurnameByProjection();
    }
}
