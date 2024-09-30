package iwo.wintech.cafemanagement.persistence.impl.admin;

import iwo.wintech.cafemanagement.entity.admin.AdminUser;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class AdminUserMapper {

    public static AdminUser mapRow(final ResultSet r, final int ignored) throws SQLException {
        return AdminUser.builder()
                .id(r.getLong("id"))
//                .roleId(r.getLong("role_id"))
                .firstName(r.getString("firstname"))
                .lastName(r.getString("lastname"))
                .email(r.getString("email"))
                .password(r.getString("password"))
                .build();
    }
}
