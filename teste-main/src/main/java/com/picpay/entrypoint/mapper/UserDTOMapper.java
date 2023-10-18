package com.picpay.entrypoint.mapper;
import com.picpay.core.domain.User;
import com.picpay.entrypoint.dto.request.RequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    User toUserDomain(RequestDTO dto);

    RequestDTO toDto(User domain);
}
