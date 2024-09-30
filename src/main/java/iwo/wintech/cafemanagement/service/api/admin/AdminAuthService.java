package iwo.wintech.cafemanagement.service.api.admin;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.rest.admin.AdminRegisterRequest;

public interface AdminAuthService {
    String adminLogin(LoginDto loginDto);
    boolean registerAdmin(AdminRegisterRequest request);
}
