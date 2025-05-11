package com.example.emtlab.repo;

import com.example.emtlab.model.views.UserMostWishedAuthorsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMostWishedAuthorsViewRepository extends JpaRepository<UserMostWishedAuthorsView, Long> {
    List<UserMostWishedAuthorsView> findByUsername(String username);
}
