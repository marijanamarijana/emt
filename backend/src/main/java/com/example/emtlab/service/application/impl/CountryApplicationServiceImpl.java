package com.example.emtlab.service.application.impl;

import com.example.emtlab.dto.CountryCreateDto;
import com.example.emtlab.dto.CountryDisplayDto;
import com.example.emtlab.dto.CountryUpdateDto;
import com.example.emtlab.service.application.CountryApplicationService;
import com.example.emtlab.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {

    private final CountryService countryService;

    public CountryApplicationServiceImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public List<CountryDisplayDto> findAll() {
        return CountryDisplayDto.from(countryService.findAll());
    }

    @Override
    public Optional<CountryDisplayDto> findById(Long id) {
        return countryService.findById(id).map(CountryDisplayDto::from);
    }
    @Override
    public Optional<CountryDisplayDto> update(Long id, CountryUpdateDto countryUpdateDto) {
        return countryService.update(id, countryUpdateDto.toCountry()).map(CountryDisplayDto::from);
    }
    @Override
    public Optional<CountryDisplayDto> save(CountryCreateDto countryCreateDto) {
        return countryService.save(countryCreateDto.toCountry()).map(CountryDisplayDto::from);    }

    @Override
    public void deleteById(Long id) {
        countryService.deleteById(id);
    }
}
