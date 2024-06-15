package com.marcos.desenvolvimento.controllers;

import com.marcos.desenvolvimento.controllers.dtos.requests.SignupUserRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.UserPostRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.UserPutPasswordRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.UserPutRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.responses.UserResponseDTO;
import com.marcos.desenvolvimento.controllers.mappers.UserMapper;
import com.marcos.desenvolvimento.usecases.CreateUser;
import com.marcos.desenvolvimento.usecases.DeleteUser;
import com.marcos.desenvolvimento.usecases.FindUser;
import com.marcos.desenvolvimento.usecases.UpdateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("v1/user")
@RestController
public class UserController {

    private final CreateUser createUser;
    private final FindUser findUser;
    private final DeleteUser deleteUser;
    private final UserMapper userMapper;
    private final UpdateUser updateUser;

    @ResponseStatus(CREATED)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO save(@RequestBody @Valid final UserPostRequestDTO userPostRequestDTO) {
        final var user = createUser.execute(userMapper.toDomain(userPostRequestDTO));
        return userMapper.fromDomain(user);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/public/save")
    public UserResponseDTO savePublicUser(@RequestBody @Valid final SignupUserRequestDTO signupUserRequestDTO) {
        final var user = createUser.execute(userMapper.toDomainUser(signupUserRequestDTO));
        return userMapper.fromDomain(user);
    }

    @ResponseStatus(OK)
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public UserResponseDTO update(@RequestBody @Valid final UserPutRequestDTO userPutRequestDTO, Principal principal) {
        final var userFinded = findUser.byLoginOrEmail(principal.getName());
        final var toJson = updateUser.update(userMapper.updateUserFromDTO(userPutRequestDTO, userFinded));
        return userMapper.fromDomain(toJson);
    }

    @ResponseStatus(OK)
    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void updatePassword(@RequestBody @Valid final UserPutPasswordRequestDTO userPassword) {
        final var userFinded = findUser.byId(userPassword.id());
        updateUser.updatePassword(userMapper.updatePasswordFromDTO(userPassword, userFinded));
    }

    @ResponseStatus(OK)
    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('USER')")
    public void updatePassword(@RequestBody @Valid final UserPutPasswordRequestDTO userPassword, Principal principal) {
        final var userFinded = findUser.byLoginOrEmail(principal.getName());
        updateUser.updateUserPassword(userMapper.updatePasswordFromDTO(userPassword, userFinded), userFinded);
    }

    @ResponseStatus(OK)
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO findById(@PathVariable final Long id) {
        final var user = findUser.byId(id);
        return userMapper.fromDomain(user);
    }

    @ResponseStatus(OK)
    @GetMapping("login/{login}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO findByLogin(@PathVariable final String login) {
        final var user = findUser.byLoginOrEmail(login);
        return userMapper.fromDomain(user);
    }


    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable final Long id) {
        deleteUser.byId(id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByIds(@RequestParam final List<Long> ids) {
        deleteUser.byIds(ids);
    }

    @ResponseStatus(OK)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return findUser.all(pageable).map(userMapper::fromDomain);
    }
}
