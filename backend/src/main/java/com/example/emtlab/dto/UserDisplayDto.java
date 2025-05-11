package com.example.emtlab.dto;

import com.example.emtlab.model.domain.User;
import com.example.emtlab.model.enumeration.Role;

public record UserDisplayDto(String username, String name, String surname, Role role) {

    public static UserDisplayDto from(User user) {
        return new UserDisplayDto(
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }
    public User toUser() {
        return new User(username, name, surname, role.name());
    }

}
