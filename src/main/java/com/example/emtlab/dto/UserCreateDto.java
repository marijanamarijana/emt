package com.example.emtlab.dto;

import com.example.emtlab.model.domain.User;
import com.example.emtlab.model.enumeration.Role;

public record UserCreateDto(String username, String password, String repeatPassword, String name, String surname, Role role) {

}
