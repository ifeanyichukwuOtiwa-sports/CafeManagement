package iwo.wintech.cafemanagement.rest.user;

import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.dto.UserInfoUpdateRequest;
import iwo.wintech.cafemanagement.rest.CafeMgmtUriMappings;
import iwo.wintech.cafemanagement.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = CafeMgmtUriMappings.USER_URI)
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("update")
    public Boolean updateInfo(final UserDto user, @RequestBody final UserInfoUpdateRequest request) {
        return userService.updateInfo(user, request);
    }
}
