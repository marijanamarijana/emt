package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Country;

public record CountryCreateDto(String name, String continent) {
    public Country toCountry(){
        return new Country(name, continent);
    }
}
