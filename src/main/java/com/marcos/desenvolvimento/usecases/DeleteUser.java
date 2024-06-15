package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.exceptions.PermissionDeniedException;
import com.marcos.desenvolvimento.gateways.UserGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public record DeleteUser(UserGateway userGateway, FindUser findUser) {

    private static final String ADMIN = "admin";

    public void byIds(final List<Long> ids) {
        ids.forEach(this::deleteValidation);
        userGateway.deleteByIds(ids);
    }

    public void byId(final Long id) {
        deleteValidation(id);
        userGateway.deleteById(id);
        log.info("[deleteUser][userId deleted: {}]", id);
    }

    private void deleteValidation(Long id) {
        final var user = findUser.byId(id);
        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (user.getLogin().equalsIgnoreCase(ADMIN)) {
            throw new PermissionDeniedException("You cannot delete a developer user");
        } else if (authentication.getName().equalsIgnoreCase(user.getLogin())) {
            throw new PermissionDeniedException("You cannot delete your own user");
        }
    }
}
