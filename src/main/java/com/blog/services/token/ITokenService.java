package com.blog.services.token;

import com.blog.entities.User;

public interface ITokenService {

    // Add Token
    Boolean addToken(String token, User user);


    // Delete Token
    Boolean deleteToken(User user);


    // Update Token
    Boolean updateToken(String token, User user);

    // Set isLogout to true
    Boolean setIsLogout(User user);
}
