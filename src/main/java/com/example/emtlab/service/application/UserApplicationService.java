package com.example.emtlab.service.application;

import com.example.emtlab.dto.*;
import java.util.List;
import java.util.Optional;

public interface UserApplicationService {
    Optional<UserDisplayDto> register(UserCreateDto createUserDto);
    Optional<UserDisplayDto> login(UserLoginDto loginUserDto);
    Optional<UserDisplayDto> findByUsername(String username);
    List<BookDisplayDto> getWishlist(String username);
    void addBookToWishlist(String username, Long bookId);
    void rentAllWishlistBooks(String username);
    List<UserDisplayDto> getAllUsers();

}
