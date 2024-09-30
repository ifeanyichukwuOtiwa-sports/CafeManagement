package iwo.wintech.cafemanagement.rest.admin;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.rest.CafeMgmtUriMappings;
import iwo.wintech.cafemanagement.service.api.admin.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CafeMgmtUriMappings.ADMIN_AUTH_URI)
@RequiredArgsConstructor
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    @PostMapping("register")
    public boolean registerAdmin(@RequestBody final AdminRegisterRequest request) {
        return adminAuthService.registerAdmin(request);
    }

    @PostMapping("authenticate")
    public String authenticateAdmin(@RequestBody final LoginDto request) {
        return adminAuthService.adminLogin(request);
    }
}
