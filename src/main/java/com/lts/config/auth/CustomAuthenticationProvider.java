package com.lts.config.auth;

import com.lts.model.entities.User;
import com.lts.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * In this class we should throw only error codes and not error messages, because they are being resolved on GET
 * request for login, in HomeController
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserRepository userRepository;

    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        User user = userRepository.getByUsername(auth.getName());
        if (user == null) {
            throw new UsernameNotFoundException("errUsernameNotFound");
        }
        Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(result.getPrincipal(), result.getCredentials(),
                result.getAuthorities());
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}