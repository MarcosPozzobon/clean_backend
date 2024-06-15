package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.gateways.UserGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record CreateUser(UserGateway userGateway) {

    public User execute(final User user) {
        log.info("[create-user: {}] creating new user", user.getLogin());
        return userGateway.save(user);
    }
}
