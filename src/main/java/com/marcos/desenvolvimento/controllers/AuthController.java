package com.marcos.desenvolvimento.controllers;


import com.marcos.desenvolvimento.controllers.dtos.requests.LoginRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.SignupUserRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.responses.MessageResponse;
import com.marcos.desenvolvimento.controllers.mappers.UserMapper;
import com.marcos.desenvolvimento.security.services.LoginService;
import com.marcos.desenvolvimento.usecases.CreateUser;
import com.marcos.desenvolvimento.usecases.FindUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FindUser findUser;
    private final CreateUser createUser;
    private final UserMapper userMapper;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        final var responseCookie = loginService.validateLogin(loginRequest);
        return ok().header(SET_COOKIE, responseCookie.toString()).body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logoutUser() {
        ResponseCookie cookie = loginService.logout();
        return ok().header(SET_COOKIE, cookie.toString()).body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupUserRequestDTO signUpRequestDTO) {
        if (TRUE.equals(findUser.existsByLogin(signUpRequestDTO.login()))) {
            return badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (TRUE.equals(findUser.existsByEmail(signUpRequestDTO.email()))) {
            return badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        createUser.execute(userMapper.toDomainUser(signUpRequestDTO));
        return ResponseEntity.status(CREATED).build();
    }
}
