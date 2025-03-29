package com.example.emtlab.service.application.impl;

import com.example.emtlab.dto.BookDisplayDto;
import com.example.emtlab.dto.UserCreateDto;
import com.example.emtlab.dto.UserDisplayDto;
import com.example.emtlab.dto.UserLoginDto;
import com.example.emtlab.model.domain.User;
import com.example.emtlab.service.application.UserApplicationService;
import com.example.emtlab.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserService userService;

    public UserApplicationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserDisplayDto> register(UserCreateDto createUserDto) {
        User user = userService.register(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.repeatPassword(),
                createUserDto.name(),
                createUserDto.surname(),
                createUserDto.role()
        );
        return Optional.of(UserDisplayDto.from(user));

    }

    @Override
    public Optional<UserDisplayDto> login(UserLoginDto loginUserDto) {
        return Optional.of(UserDisplayDto.from(userService.login(
                loginUserDto.username(),
                loginUserDto.password()
        )));
    }

    @Override
    public Optional<UserDisplayDto> findByUsername(String username) {
        return Optional.of(UserDisplayDto.from(userService.findByUsername(username)));
    }

    @Override
    public List<BookDisplayDto> getWishlist(String username) {
        return userService.getWishlist(username).stream().map(BookDisplayDto::from).collect(Collectors.toList());
    }

    @Override
    public void addBookToWishlist(String username, Long bookId) {
        userService.addBookToWishlist(username, bookId);
    }

    @Override
    public void rentAllWishlistBooks(String username) {
        userService.rentAllWishlistBooks(username);
    }

    @Override
    public List<UserDisplayDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserDisplayDto::from)
                .toList();
    }
}
