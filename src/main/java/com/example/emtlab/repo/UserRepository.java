package com.example.emtlab.repo;

import com.example.emtlab.model.domain.Author;
import com.example.emtlab.model.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"booksWishlist"}
    )
    @Query("select u from User u")
    List<User> loadAll();

//    SELECT DISTINCT a.*
//    FROM author a
//    JOIN book b ON b.author_id = a.id
//    JOIN book_users_books_wishlist bw ON bw.books_wishlist_id = b.id
//    JOIN book_users u ON u.username = bw.book_users_username
//    WHERE u.username = :username
    @Query("SELECT DISTINCT b.author FROM User u JOIN u.booksWishlist b WHERE u.username = :username")
    List<Author> getFavoriteAuthorsByUsername(String username);
}
