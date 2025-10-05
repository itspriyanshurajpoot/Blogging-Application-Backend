package com.blog.services.token;

import com.blog.entities.Token;
import com.blog.entities.User;
import com.blog.repositories.TokenRepository;
import com.blog.utils.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Boolean addToken(String token, User user) {
        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setUser(user);
        newToken.setLoginCount(1);
        newToken.setIsLogout(false);

        Token savedToken = tokenRepository.save(newToken);
        return savedToken == null ? false : true;
    }

    @Override
    public Boolean deleteToken(User user) {
        tokenRepository.findTokenByUser(user)
                .ifPresentOrElse(tokenRepository::delete, () -> {
                    throw new ResourceNotFoundException("Token", "user", user.getEmail());
                });
        return true;
    }

    @Override
    public Boolean updateToken(String token, User user) {
        // Find token by user
        Token existingToken = tokenRepository.findTokenByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "user", user.getEmail()));


        // Update the token, updatedAt and count property
        existingToken.setToken(token);
        existingToken.setLoginCount(existingToken.getLoginCount() + 1);
        existingToken.setIsLogout(false);
        existingToken.setUpdatedAt(LocalDateTime.now());


        // Save the token
        Token savedToken = tokenRepository.save(existingToken);
        return savedToken != null ? true: false;
    }

    @Override
    public Boolean setIsLogout(User user) {
        Token existingToken = tokenRepository.findTokenByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "user", user.getEmail()));

        existingToken.setIsLogout(true);
        existingToken.setUpdatedAt(LocalDateTime.now());
        Token savedToken = tokenRepository.save(existingToken);

        return savedToken != null ? true : false;
    }
}
