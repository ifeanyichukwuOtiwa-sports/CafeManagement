package iwo.wintech.cafemanagement.service.mapper;

import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Converter {

    @Mapping(target = "id", source = "userId")
    UserDto convert(final User user);
}
