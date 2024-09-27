package iwo.wintech.cafemanagement.persistence.impl;

import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.persistence.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcClient dbClient;

    @Override
    public Optional<User> findByEmail(final String email) {
        final String sql = """
                SELECT user_id, first_name, last_name, email, phone_number, status, role, password
                FROM user
                """;
        final SqlParameterSource params = new MapSqlParameterSource("email", email);
        return dbClient.sql(sql)
                .paramSource(params)
                .query(UserEntityMapper::mapRow)
                .optional();
    }

    @Override
    public void save(final User user) {
        final String sql = """
                INSERT INTO user (first_name, last_name, email, phone_number, status, role, password)
                VALUES (:firstName, :lastName, :email, :phoneNumber, :status, :role, :password)
                """;
        final SqlParameterSource params = new MapSqlParameterSource()
               .addValue("firstName", user.getFirstName())
               .addValue("lastName", user.getLastName())
               .addValue("email", user.getEmail())
               .addValue("phoneNumber", user.getPhoneNumber())
               .addValue("status", user.getStatus())
               .addValue("role", user.getRole())
               .addValue("password", user.getPassword());

        dbClient.sql(sql)
                .paramSource(params)
                .update();
    }
}
