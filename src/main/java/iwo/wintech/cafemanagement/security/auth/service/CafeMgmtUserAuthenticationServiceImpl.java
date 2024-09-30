package iwo.wintech.cafemanagement.security.auth.service;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.dto.SignupDto;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.exception.ErrorCode;
import iwo.wintech.cafemanagement.rest.user.ChangePasswordRequest;
import iwo.wintech.cafemanagement.security.TemporaryLoginHolder;
import iwo.wintech.cafemanagement.service.api.CafeMgmtUserAuthenticationService;
import iwo.wintech.cafemanagement.service.api.UserService;
import iwo.wintech.cafemanagement.service.events.EmailMessageType;
import iwo.wintech.cafemanagement.service.events.UserNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CafeMgmtUserAuthenticationServiceImpl implements CafeMgmtUserAuthenticationService {
    private final UserService userService;
    private final TemporaryLoginHolder holder;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;


    public String loginUser(final LoginDto loginDto) {
        final String email = loginDto.email();
        final String password = loginDto.password();
        final Optional<User> optionalUser = this.validateUser(email, password);
        final Boolean response = optionalUser.isPresent();
        if (Boolean.TRUE.equals(response)) {

            return holder.createSession(optionalUser.get());
        }
        throw ErrorCode.INVALID_CREDENTIALS.requestException("Invalid Login Credentials");
    }

    @Override
    public boolean checkToken(final String token) {
        return holder.validateToken(token);
    }

    @Override
    public String changePassword(final ChangePasswordRequest request, final UserDto user) {
        final User userToBeUpdated = userService.findByEmail(user.email());
        if (userToBeUpdated == null) {
            throw ErrorCode.USER_NOT_FOUND.requestException("User does not exists");
        }
        validatePasswords(request, userToBeUpdated.getPassword());

        updatePassword(request.confirmPassword(), userToBeUpdated, "your new password");
        return "password Updated";
    }

    private void updatePassword(final String newPassword, final User user, final String notificationText) {
        final Long id = user.getUserId();
        userService.updateUserPassword(id, passwordEncoder.encode(newPassword));
        applicationEventPublisher.publishEvent(new UserNotificationEvent(id, EmailMessageType.PASSWORD_RESET, notificationText));
        holder.invalidate(user);
    }

    @Override
    public String forgetPassword(final String email) {
        final User user = userService.findByEmail(email);
        if (user == null) {
            throw ErrorCode.USER_NOT_FOUND.requestException("User does not exist");
        }

        final String newPassword = RandomStringUtils.randomAlphabetic(8);

        updatePassword(newPassword, user, newPassword);

        return "Password sent to your registered email";
    }

    private void validatePasswords(final ChangePasswordRequest request, final String password) {
        if (!passwordEncoder.matches(request.currentPassword(), password)) {
            throw ErrorCode.INVALID_CREDENTIALS.requestException("password is incorrect");
        }

        if (!request.isPasswordValid()) {
            throw ErrorCode.INVALID_CREDENTIALS.requestException("New passwords do not match");
        }

        if (request.newPasswordMatchesOldPassword()) {
            throw ErrorCode.INVALID_CREDENTIALS.requestException("New password cannot be the same as old password");
        }
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
        if (user == null) {
            throw ErrorCode.USER_NOT_FOUND.requestException("User not found with the provided email %s".formatted(email));
        }
        if (!("true".equals(user.getStatus()))) {
            throw ErrorCode.USER_NOT_ACTIVE.requestException("User is not yet activated. please contact admin");
        }
        return passwordMatches(rawPassword, user.getPassword()) ? Optional.of(user) : Optional.empty();
    }

    private boolean passwordMatches(final String rawPassword, final String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String encodePassword(final String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
