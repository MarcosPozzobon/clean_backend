package com.marcos.desenvolvimento.security.services;

import com.marcos.desenvolvimento.controllers.dtos.requests.LoginRequestDTO;
import com.marcos.desenvolvimento.security.jwt.TokenUtils;
import com.marcos.desenvolvimento.usecases.FindUser;
import com.marcos.desenvolvimento.usecases.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final FindUser findUser;
    private final UpdateUser updateUser;

    public ResponseCookie validateLogin(final LoginRequestDTO loginRequest) {
        final var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));

        var userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        addLastAccess(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return tokenUtils.generateToken(userDetails);
    }

    public ResponseCookie logout() {
        return tokenUtils.cleanToken();
    }

    private void addLastAccess(final UserDetailsImpl authentication) {
        final String username = authentication.getUsername();
        final var user = findUser.byLoginOrEmail(username);
        user.setLastAccess();
        updateUser.update(user);
    }
}
