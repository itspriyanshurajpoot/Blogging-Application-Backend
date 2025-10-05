package com.blog.repositories;

import com.blog.entities.Token;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findTokenByUser(User user);
    Optional<Token> findTokenByUserEmail(String email);
}
