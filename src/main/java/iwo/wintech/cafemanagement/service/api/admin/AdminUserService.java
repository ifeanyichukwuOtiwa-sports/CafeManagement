package iwo.wintech.cafemanagement.service.api.admin;

import iwo.wintech.cafemanagement.entity.admin.AdminUser;

public interface AdminUserService {
    boolean register(AdminUser request);
    AdminUser findByEmail(String email);
}
