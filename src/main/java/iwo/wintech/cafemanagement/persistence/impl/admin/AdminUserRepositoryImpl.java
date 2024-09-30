package iwo.wintech.cafemanagement.persistence.impl.admin;

import iwo.wintech.cafemanagement.entity.admin.AdminUser;
import iwo.wintech.cafemanagement.persistence.api.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AdminUserRepositoryImpl implements AdminUserRepository {
    private final JdbcClient jdbcClient;

    @Override
    public void saveAdmin(final AdminUser user) {
        final String sql = """
                INSERT INTO admin_user (firstname, lastname, email, password)
                VALUES (:firstName, :lastName, :email, :password)
                """;

        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());

        jdbcClient.sql(sql)
                .paramSource(params)
                .update();
    }

    @Override
    public Optional<AdminUser> findAdminByEmail(final String email) {
        final String sql = """
                SELECT id, role_id, firstname, lastname, email, password
                FROM admin_user
                WHERE email = :email
                """;

        final SqlParameterSource params = new MapSqlParameterSource("email", email);

        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(AdminUserMapper::mapRow)
                .optional();
    }

    @Override
    public Optional<AdminUser> findAdminById(final Long id) {
        final String sql = """
                SELECT id, role_id, firstname, lastname, email, password
                FROM admin_user
                WHERE id = :id
                """;

        final SqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcClient.sql(sql)
                .paramSource(params)
                .query(AdminUserMapper::mapRow)
                .optional();
    }
}
