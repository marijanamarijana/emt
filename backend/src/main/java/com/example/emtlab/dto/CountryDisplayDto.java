package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Country;
import java.util.List;
import java.util.stream.Collectors;

public record CountryDisplayDto(Long id, String name, String continent) {
    public static CountryDisplayDto from(Country country){
        return new CountryDisplayDto(country.getId(), country.getName(), country.getContinent());
    }
    public static List<CountryDisplayDto> from(List<Country> countries){
        return countries.stream().map(CountryDisplayDto::from).collect(Collectors.toList());
    }

}
