package com.example.emtlab.service.domain.impl;

import com.example.emtlab.model.domain.Country;
import com.example.emtlab.repo.CountryRepository;
import com.example.emtlab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Optional<Country> update(Long id, Country country) {
        return countryRepository.findById(id)
                .map(existingCountry -> {
                    if (country.getName() != null) {
                        existingCountry.setName(country.getName());
                    }
                    if (existingCountry.getContinent() != null) {
                        existingCountry.setContinent(country.getContinent());
                    }
                    return countryRepository.save(existingCountry);
                });

    }

    @Override
    public Optional<Country> save(Country country) {
        return Optional.of(
                countryRepository.save(new Country(
                       country.getName(), country.getContinent()
                )));

    }
    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }
}
