package com.example.emtlab.service.application;

import com.example.emtlab.dto.*;
import com.example.emtlab.model.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserApplicationService {
    Optional<UserDisplayDto> register(UserCreateDto createUserDto);
    Optional<LoginResponseDto> login(UserLoginDto loginUserDto);
    Optional<UserDisplayDto> findByUsername(String username);
    List<BookDisplayDto> getWishlist(String username);
    void addBookToWishlist(String username, Long bookId);
    void rentAllWishlistBooks(String username);
    List<UserDisplayDto> getAllUsers();
    List<AuthorDisplayDto> getFavoriteAuthorsByUsername(String username);
}
