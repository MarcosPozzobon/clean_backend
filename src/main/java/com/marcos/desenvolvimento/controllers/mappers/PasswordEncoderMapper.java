package com.marcos.desenvolvimento.controllers.mappers;

import com.marcos.desenvolvimento.annotations.EncodedMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public record PasswordEncoderMapper(PasswordEncoder passwordEncoder) {

    @EncodedMapping
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
