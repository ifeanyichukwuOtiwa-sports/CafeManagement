package iwo.wintech.cafemanagement.service.mapper;

import iwo.wintech.cafemanagement.dto.AdminDto;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.entity.admin.AdminUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface Converter {

    @Mapping(target = "id", source = "userId")
    UserDto convert(final User user);


    AdminDto convert(final AdminUser user);
}
