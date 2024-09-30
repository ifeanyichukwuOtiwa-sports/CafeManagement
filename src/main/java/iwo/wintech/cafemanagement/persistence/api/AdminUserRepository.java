package iwo.wintech.cafemanagement.persistence.api;

import iwo.wintech.cafemanagement.entity.admin.AdminUser;

import java.util.Optional;

public interface AdminUserRepository {

    void saveAdmin(AdminUser user);
    Optional<AdminUser> findAdminByEmail(String email);
    Optional<AdminUser> findAdminById(Long id);

}
