package iwo.wintech.cafemanagement.rest;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.dto.SignupDto;
import iwo.wintech.cafemanagement.service.api.CafeMgmtUserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user/auth")
@RestController
public class UserAuthController {


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
}
