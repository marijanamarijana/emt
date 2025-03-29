package com.example.emtlab.dto;

import com.example.emtlab.model.domain.Country;

public record CountryUpdateDto (String name, String continent) {
    public Country toCountry(){
        return new Country(name, continent);
    }
}
