package com.example.emtlab.service.domain.impl;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.Book;
import com.example.emtlab.model.domain.User;
import com.example.emtlab.model.enumeration.Role;
import com.example.emtlab.model.exceptions.*;
import com.example.emtlab.model.views.UserMostWishedAuthorsView;
import com.example.emtlab.repo.UserMostWishedAuthorsViewRepository;
import com.example.emtlab.repo.UserRepository;
import com.example.emtlab.service.domain.BookService;
import com.example.emtlab.service.domain.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;
    private final UserMostWishedAuthorsViewRepository userMostWishedAuthorsViewRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, BookService bookService, UserMostWishedAuthorsViewRepository userMostWishedAuthorsViewRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookService = bookService;
        this.userMostWishedAuthorsViewRepository = userMostWishedAuthorsViewRepository;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();
        if (userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return userRepository.save(user);

    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new InvalidUserCredentialsException();
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<Book> getWishlist(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBooksWishlist();
    }

    @Override
    public void addBookToWishlist(String username, Long bookId) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookService.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies for this book");
        }

        if (!user.getBooksWishlist().contains(book)) {
            user.getBooksWishlist().add(book);
            userRepository.save(user);
        }
    }

    @Override
    public void rentAllWishlistBooks(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Book> wishlist = new ArrayList<>(user.getBooksWishlist());

        for (Book book : wishlist) {
            if (book.getAvailableCopies() > 0) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                bookService.save(book);
            } else {
                throw new RuntimeException("Book " + book.getName() + " is not available for renting");
            }
        }

        user.getBooksWishlist().clear();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                username));
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.loadAll();
        //return userRepository.findAll();
    }

    @Override
    public List<Author> getFavoriteAuthorsByUsername(String username) {
        //        User user = userRepository.findById(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Set<Author> authors= new HashSet<>();
//        for (int i = 0; i < user.getBooksWishlist().size(); i++) {
//            authors.add(user.getBooksWishlist().get(i).getAuthor());
//        }
//        return authors.stream().toList();
//        }
        return userRepository.getFavoriteAuthorsByUsername(username);
    }
    @Override
    public List<UserMostWishedAuthorsView> findUserMostWishedAuthorsViewByByUserId(String username) {
        return userMostWishedAuthorsViewRepository.findByUsername(username);
    }
    }

