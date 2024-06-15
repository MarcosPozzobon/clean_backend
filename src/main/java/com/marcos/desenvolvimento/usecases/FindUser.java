package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.gateways.UserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public record FindUser(UserGateway userGateway) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public User byId(final Long id) {
        return userGateway.findById(id);
    }

    public User byLoginOrEmail(String loginOrEmail) {
        boolean isEmail = isEmail(loginOrEmail);

        if (isEmail) {
            return userGateway.findByEmail(loginOrEmail);
        } else {
            return userGateway.findByLogin(loginOrEmail);
        }
    }

    public Boolean existsByLogin(final String login) {
        return userGateway.existsByLogin(login);
    }

    public Boolean existsByEmail(final String email) {
        return userGateway.existsByEmail(email);
    }

    public Page<User> all(Pageable pageable) {
        return userGateway.getAll(pageable);
    }

    private Boolean isEmail(String username) {
        if (username == null) {
            return false;
        }

        return EMAIL_PATTERN.matcher(username).matches();
    }
}
