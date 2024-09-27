package iwo.wintech.cafemanagement.security.auth.service;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.dto.SignupDto;
import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.security.TemporaryLoginHolder;
import iwo.wintech.cafemanagement.service.api.CafeMgmtUserAuthenticationService;
import iwo.wintech.cafemanagement.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class CafeMgmtUserAuthenticationServiceImpl implements CafeMgmtUserAuthenticationService {
    private final UserService userService;
    private final TemporaryLoginHolder holder;
    private final PasswordEncoder passwordEncoder;


    public String loginUser(final LoginDto loginDto) {
        final String email = loginDto.email();
        final String password = loginDto.password();
        final Optional<User> optionalUser = this.validateUser(email, password);
        final Boolean response = optionalUser.isPresent();
        if (Boolean.TRUE.equals(response)) {

            return holder.createSession(optionalUser.get());
        }
        throw new RuntimeException("Invalid Login Credentials");
    }

    public String registerUser(final SignupDto request) {
        final User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(encodePassword(request.password()))
                .role("ROLE_USER")
                .status("false")
                .phoneNumber(request.phoneNumber())
                .build();
        return userService.registerUser(user);
    }


    private Optional<User> validateUser(final String email, final String rawPassword) {
        final User user = userService.findByEmail(email);
        log.info("User: {}", user);
        return user!= null && passwordMatches(rawPassword, user.getPassword()) ? Optional.of(user) : Optional.empty();
    }

    private boolean passwordMatches(final String rawPassword, final String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String encodePassword(final String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
