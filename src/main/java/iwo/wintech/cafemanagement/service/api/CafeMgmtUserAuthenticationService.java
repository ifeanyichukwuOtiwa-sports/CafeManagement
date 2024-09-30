package iwo.wintech.cafemanagement.service.api;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.dto.SignupDto;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.rest.user.ChangePasswordRequest;

public interface CafeMgmtUserAuthenticationService {
    String registerUser(SignupDto request);
    String loginUser(LoginDto loginDto);
    boolean checkToken(String user);

    String changePassword(ChangePasswordRequest request, UserDto user);
    String forgetPassword(String email);
}
