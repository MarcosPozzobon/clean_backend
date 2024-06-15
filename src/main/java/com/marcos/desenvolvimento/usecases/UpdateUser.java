package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.exceptions.PermissionDeniedException;
import com.marcos.desenvolvimento.gateways.UserGateway;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public record UpdateUser(UserGateway userGateway, FindUser findUser) {

    private static final String ADMIN = "admin";

    public User update(final User user) {
        if (user.getId() != null && lastLogin(user.getId()).equals(ADMIN)) {
            validationLoginChange(user);
        }

        return userGateway.update(user);
    }

    public void updateUserPassword(final User user, User userFinded) {
        if (!userFinded.getPassword().equals(user.getPassword())) {
            throw new PermissionDeniedException("Incorrect password!");
        }

        if (user.getLogin().equalsIgnoreCase(ADMIN)) {
            throw new PermissionDeniedException("You cannot change a developer password");
        }
        update(user);
    }

    public void updatePassword(final User user) {
        if (user.getLogin().equalsIgnoreCase(ADMIN)) {
            throw new PermissionDeniedException("You cannot change a developer password");
        }
        update(user);
    }

    private void validationLoginChange(final User user) {
        if (!Objects.equals(user.getLogin(), ADMIN)) {
            throw new PermissionDeniedException("You cannot change a developer login");
        }
    }

    private String lastLogin(final Long id) {
        final var user = findUser.byId(id);
        return user.getLastLogin();
    }
}
