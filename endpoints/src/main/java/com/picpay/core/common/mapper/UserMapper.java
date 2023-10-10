package com.picpay.core.common.mapper;

import com.picpay.core.domain.User;
import com.picpay.dataprovider.database.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUserDomain(UserEntity user);
}
