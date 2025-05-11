package com.example.emtlab.service.application;

import com.example.emtlab.dto.CountryCreateDto;
import com.example.emtlab.dto.CountryDisplayDto;
import com.example.emtlab.dto.CountryUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CountryApplicationService {
    List<CountryDisplayDto> findAll();
    Optional<CountryDisplayDto> findById(Long id);
    Optional<CountryDisplayDto> update(Long id, CountryUpdateDto countryUpdateDto);
    Optional<CountryDisplayDto> save(CountryCreateDto countryCreateDto);
    void deleteById(Long id);
}
