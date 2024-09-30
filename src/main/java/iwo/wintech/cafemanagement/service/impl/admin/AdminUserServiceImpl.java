package iwo.wintech.cafemanagement.service.impl.admin;

import iwo.wintech.cafemanagement.dto.LoginDto;
import iwo.wintech.cafemanagement.entity.admin.AdminUser;
import iwo.wintech.cafemanagement.persistence.api.AdminUserRepository;
import iwo.wintech.cafemanagement.service.api.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository adminUserRepository;


    @Override
    public boolean register(final AdminUser request) {
        adminUserRepository.saveAdmin(request);
        return true;
    }

    @Override
    public AdminUser findByEmail(final String email) {
        return adminUserRepository.findAdminByEmail(email).orElse(null);
    }
}
