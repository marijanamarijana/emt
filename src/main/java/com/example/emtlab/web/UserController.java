package com.example.emtlab.web;

import com.example.emtlab.dto.BookDisplayDto;
import com.example.emtlab.dto.UserCreateDto;
import com.example.emtlab.dto.UserDisplayDto;
import com.example.emtlab.dto.UserLoginDto;
import com.example.emtlab.model.exceptions.InvalidArgumentsException;
import com.example.emtlab.model.exceptions.InvalidUserCredentialsException;
import com.example.emtlab.model.exceptions.PasswordsDoNotMatchException;
import com.example.emtlab.service.application.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations related to users")
public class UserController {
    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully"
            ), @ApiResponse(
                    responseCode = "400", description = "Invalid input or passwords do not match"
            )}
    )
    @PostMapping("/register")
    public ResponseEntity<UserDisplayDto> register(@RequestBody UserCreateDto createUserDto) {
        try {
            return userApplicationService.register(createUserDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "User login", description = "Authenticates a user and starts a session")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully"
            ), @ApiResponse(responseCode = "404", description = "Invalid username or password")}
    )
    @PostMapping("/login")
    public ResponseEntity<UserDisplayDto> login(HttpServletRequest request) {
        try {
            UserDisplayDto displayUserDto = userApplicationService.login(
                    new UserLoginDto(request.getParameter("username"), request.getParameter("password"))
            ).orElseThrow(InvalidUserCredentialsException::new);

            request.getSession().setAttribute("user", displayUserDto.toUser());
            return ResponseEntity.ok(displayUserDto);
        } catch (InvalidUserCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "User logout", description = "Ends the user's session")
    @ApiResponse(responseCode = "200", description = "User logged out successfully")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @Operation(summary = "Get user's wishlist", description = "Returns the list of books in the user's wishlist.")
    @GetMapping("/wishlist/{username}")
    public List<BookDisplayDto> getWishlist(@PathVariable String username) {
        return userApplicationService.getWishlist(username);
    }

    @Operation(summary = "Add book to wishlist", description = "Adds a book to the user's wishlist if copies are available.")
    @PostMapping("/wishlist/add/{username}/{bookId}")
    public ResponseEntity<Void> addBookToWishlist(@PathVariable String username, @PathVariable Long bookId) {
        try {
            userApplicationService.addBookToWishlist(username, bookId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Rent all books from wishlist", description = "Rents all books from the wishlist and removes them from it.")
    @PostMapping("/wishlist/rent/{username}")
    public ResponseEntity<Void> rentAllWishlistBooks(@PathVariable String username) {
        try {
            userApplicationService.rentAllWishlistBooks(username);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    @GetMapping
    public ResponseEntity<List<UserDisplayDto>> getAllUsers() {
        return ResponseEntity.ok(userApplicationService.getAllUsers());
    }

}
