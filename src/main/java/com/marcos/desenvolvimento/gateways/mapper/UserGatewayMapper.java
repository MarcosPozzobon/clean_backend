package com.marcos.desenvolvimento.gateways.mapper;

import com.marcos.desenvolvimento.domain.User;
import com.marcos.desenvolvimento.gateways.entities.UserEntity;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface UserGatewayMapper {

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);

}