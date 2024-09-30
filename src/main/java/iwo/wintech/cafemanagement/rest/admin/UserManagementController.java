package iwo.wintech.cafemanagement.rest.admin;

import iwo.wintech.cafemanagement.dto.AdminDto;
import iwo.wintech.cafemanagement.dto.ListResponse;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.rest.CafeMgmtUriMappings;
import iwo.wintech.cafemanagement.service.api.CafeMgmtUserAuthenticationService;
import iwo.wintech.cafemanagement.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(CafeMgmtUriMappings.USER_MANAGEMENT_URI)
@RequiredArgsConstructor
@RestController
public class UserManagementController {
    private final UserService userService;
    private final CafeMgmtUserAuthenticationService authService;

    @GetMapping("list")
    public ListResponse<UserDto> getAllUser(final AdminDto admin) {
        final List<UserDto> dtos = userService.getAllUsers(admin);
        return ListResponse.of(dtos);
    }

    @PostMapping("/update-status")
    public void enableUsers(@RequestBody final List<String> userIds, final AdminDto admin) {
        userService.enableUsers(userIds, admin);
    }

    @GetMapping("check-token")
    public Boolean checkToken(final @RequestBody String token, final AdminDto admin) {
        return authService.checkToken(token, admin);
    }

}
