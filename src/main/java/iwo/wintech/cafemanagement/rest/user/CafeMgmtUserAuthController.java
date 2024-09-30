package iwo.wintech.cafemanagement.rest.user;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.dto.SignupDto;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.rest.CafeMgmtUriMappings;
import iwo.wintech.cafemanagement.service.api.CafeMgmtUserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(path = CafeMgmtUriMappings.USER_AUTH_URI)
@RestController
public class CafeMgmtUserAuthController {


    private final CafeMgmtUserAuthenticationService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody final SignupDto request) {
        final String response = authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginDto loginDto) {
        final String token = authService.loginUser(loginDto);
        return ResponseEntity.of(Optional.ofNullable(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody final String email) {
        final String response = authService.forgetPassword(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("check-token")
    public Boolean checkToken(final @RequestHeader("x-access-token") String token) {
        return authService.checkToken(token);
    }

    @PostMapping("change-password")
    public ResponseEntity<String> changePassword(@RequestBody final ChangePasswordRequest request, final UserDto user) {
        final String response = authService.changePassword(request, user);
        return ResponseEntity.ok(response);
    }
}
