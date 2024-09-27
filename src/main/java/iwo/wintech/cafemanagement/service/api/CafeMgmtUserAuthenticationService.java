package iwo.wintech.cafemanagement.service.api;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.dto.SignupDto;

public interface CafeMgmtUserAuthenticationService {
    String registerUser(SignupDto request);
    String loginUser(LoginDto loginDto);
}
