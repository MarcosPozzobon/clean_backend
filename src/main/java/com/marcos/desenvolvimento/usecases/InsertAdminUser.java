package com.marcos.desenvolvimento.usecases;

import com.marcos.desenvolvimento.gateways.entities.UserEntity;
import com.marcos.desenvolvimento.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public record InsertAdminUser(UserRepository userRepository) {

    private static final String ADMIN = "admin";

    public void insertAdminUser() {
        if (userRepository.findByLoginIgnoreCase(ADMIN).isEmpty()) {
            log.debug("Administrator user not found, creating...");
            final var user = new UserEntity();
            user.setName("Administrator");
            user.setLogin(ADMIN);
            user.setAdmin(true);
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode("baguvix"));
            user.setEmail("email@email.com");
            userRepository.save(user);
        } else {
            log.info("insertAdminUser: admin user already exists");
        }
    }
}
