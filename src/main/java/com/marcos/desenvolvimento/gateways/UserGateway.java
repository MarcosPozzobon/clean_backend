package com.marcos.desenvolvimento.gateways;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.exceptions.InvalidCredentialsException;
import com.marcos.desenvolvimento.exceptions.UserNotFoundException;
import com.marcos.desenvolvimento.gateways.mapper.UserGatewayMapper;
import com.marcos.desenvolvimento.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public record UserGateway(UserRepository userRepository, UserGatewayMapper userGatewayMapper) {

    public User save(final User user) {
        return userGatewayMapper.toDomain(userRepository.save(userGatewayMapper.toEntity(user)));
    }

    public Page<User> getAll(final Pageable pageable) {
        return userRepository.findAll(pageable).map(userGatewayMapper::toDomain);
    }

    public User findById(final Long id) {
        return userGatewayMapper.toDomain(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public User findByLogin(final String login) {
        return userGatewayMapper.toDomain(userRepository.findByLoginIgnoreCase(login).orElseThrow(InvalidCredentialsException::new));
    }

    public User findByEmail(final String email) {
        return userGatewayMapper.toDomain(userRepository.findByEmailIgnoreCase(email).orElseThrow(InvalidCredentialsException::new));
    }

    public User update(final User user) {
        return userGatewayMapper.toDomain(userRepository.save(userGatewayMapper.toEntity(user)));
    }

    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    public void deleteByIds(final List<Long> id) {
        userRepository.deleteAllById(id);
    }

    public Boolean existsByLogin(final String login) {
        return userRepository.existsByLoginIgnoreCase(login);
    }

    public Boolean existsByEmail(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
