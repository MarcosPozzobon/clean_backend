package com.marcos.desenvolvimento.controllers.mappers;


import com.marcos.desenvolvimento.annotations.EncodedMapping;
import com.marcos.desenvolvimento.controllers.dtos.requests.SignupUserRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.UserPostRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.UserPutPasswordRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.requests.UserPutRequestDTO;
import com.marcos.desenvolvimento.controllers.dtos.responses.UserResponseDTO;
import com.marcos.desenvolvimento.domain.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User toDomain(UserPostRequestDTO userPostRequestDTO);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "admin", expression = "java(false)")
    @Mapping(target = "active", expression = "java(true)")
    User toDomainUser(SignupUserRequestDTO signupUserRequestDTO);

    UserResponseDTO fromDomain(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromDTO(UserPutRequestDTO userPutRequestDTO, @MappingTarget User user);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User updatePasswordFromDTO(UserPutPasswordRequestDTO userPutPasswordRequestDTO, @MappingTarget User user);

}
