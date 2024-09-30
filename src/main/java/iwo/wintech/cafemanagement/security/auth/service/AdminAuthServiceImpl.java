package iwo.wintech.cafemanagement.security.auth.service;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.entity.admin.AdminUser;
import iwo.wintech.cafemanagement.exception.ErrorCode;
import iwo.wintech.cafemanagement.rest.admin.AdminRegisterRequest;
import iwo.wintech.cafemanagement.security.TemporaryLoginHolder;
import iwo.wintech.cafemanagement.service.api.admin.AdminAuthService;
import iwo.wintech.cafemanagement.service.api.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminAuthServiceImpl implements AdminAuthService {
    private final AdminUserService adminUserService;
    private final PasswordEncoder passwordEncoder;
    private final TemporaryLoginHolder holder;

    @Override
    public String adminLogin(final LoginDto loginDto) {
        final String password = loginDto.password();
        final String email = loginDto.email();
        final Optional<AdminUser> optionalUser = this.validateAdmin(email, password);
        final boolean response = optionalUser.isPresent();
        if (response) {
            return holder.createAdminSession(optionalUser.get());
        }

        throw ErrorCode.INVALID_ADMIN_CREDENTIALS.requestException("Invalid admin credentials");
    }

    private Optional<AdminUser> validateAdmin(final String email, final String password) {
        final AdminUser user = adminUserService.findByEmail(email);
        return Optional.ofNullable(user)
                .filter(u -> passwordMatches(password, u.getPassword()));
    }

    private boolean passwordMatches(final String rawPass, final String encodedPassword) {
        return passwordEncoder.matches(rawPass, encodedPassword);
    }

    @Override
    public boolean registerAdmin(final AdminRegisterRequest request) {

        final AdminUser user = AdminUser.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .roleId(1L)
                .build();
        return adminUserService.register(user);
    }
}
