package com.example.emtlab.service.application.impl;

import com.example.emtlab.dto.*;
import com.example.emtlab.model.domain.User;
import com.example.emtlab.security.JwtHelper;
import com.example.emtlab.service.application.UserApplicationService;
import com.example.emtlab.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserService userService;
    private final JwtHelper jwtHelper;

    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
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
    public Optional<LoginResponseDto> login(UserLoginDto userLoginDto) {
        User user = userService.login(
                userLoginDto.username(),
                userLoginDto.password()
        );

        String token = jwtHelper.generateToken(user);

        return Optional.of(new LoginResponseDto(token));
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

    @Override
    public List<AuthorDisplayDto> getFavoriteAuthorsByUsername(String username) {
        return userService.getFavoriteAuthorsByUsername(username)
                .stream()
                .map(AuthorDisplayDto::from)
                .toList();
    }
}
