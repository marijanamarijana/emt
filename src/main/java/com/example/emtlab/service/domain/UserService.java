package com.example.emtlab.service.domain;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Book;
import com.example.emtlab.model.domain.User;
import com.example.emtlab.model.enumeration.Role;
import com.example.emtlab.model.views.UserMostWishedAuthorsView;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
    User login(String username, String password);
    User findByUsername(String username);
    List<Book> getWishlist(String username);
    void addBookToWishlist(String username, Long bookId);
    void rentAllWishlistBooks(String username);
    List<User> getAllUsers();
    List<Author> getFavoriteAuthorsByUsername(String username);
    List<UserMostWishedAuthorsView> findUserMostWishedAuthorsViewByByUserId(String username);
}
