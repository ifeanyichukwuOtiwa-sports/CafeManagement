package iwo.wintech.cafemanagement.persistence.impl;

import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.persistence.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<User> fetchByStatus(final String status) {
        final String sql = """
                SELECT user_id, first_name, last_name, email, phone_number, status, role, password
                FROM user
                WHERE status = :status
                """;
        final SqlParameterSource params = new MapSqlParameterSource("status", status);
        return dbClient.sql(sql)
               .paramSource(params)
               .query(UserEntityMapper::mapRow)
               .list();
    }

    @Override
    public List<User> getAllUsers() {
        final String sql = """
                SELECT user_id, first_name, last_name, email, phone_number, status, role, password
                FROM user
                """;
        return dbClient.sql(sql)
               .query(UserEntityMapper::mapRow)
               .list();
    }

    @Override
    public List<User> findAllUsersById(final List<Long> userIds) {
        final String sql = """
                SELECT user_id, first_name, last_name, email, phone_number, status, role, password
                FROM user
                WHERE user_id IN (:userIds)
                """;
        final SqlParameterSource params = new MapSqlParameterSource("userIds", userIds);
        return dbClient.sql(sql)
               .paramSource(params)
               .query(UserEntityMapper::mapRow)
               .list();
    }

    @Override
    public List<User> getAllUsersByRole(final String role) {
        final String sql = """
                SELECT user_id, first_name, last_name, email, phone_number, status, role, password
                FROM user
                WHERE role = :role
                """;
        final SqlParameterSource params = new MapSqlParameterSource("role", role);
        return dbClient.sql(sql)
               .paramSource(params)
               .query(UserEntityMapper::mapRow)
               .list();
    }

    @Override
    public Optional<User> findById(final Long id) {
        final String sql = """
                SELECT user_id, first_name, last_name, email, phone_number, status, role, password
                FROM user
                WHERE user_id = :id
                """;
        final SqlParameterSource params = new MapSqlParameterSource("id", id);
        return dbClient.sql(sql)
                .paramSource(params)
                .query(UserEntityMapper::mapRow)
                .optional();
    }

    @Override
    public void updateUser(final User updateRequest) {
        final String sql = """
                UPDATE user
                SET first_name = :firstName, last_name = :lastName, phone_number = :phoneNumber
                WHERE user_id = :id
                """;
        final SqlParameterSource params = new MapSqlParameterSource()
               .addValue("id", updateRequest.getUserId())
               .addValue("firstName", updateRequest.getFirstName())
               .addValue("lastName", updateRequest.getLastName())
               .addValue("phoneNumber", updateRequest.getPhoneNumber());

        dbClient.sql(sql)
               .paramSource(params)
               .update();
    }

    @Override
    public void updateUsersStatus(final List<Long> userIds, final String status) {
        final String sql = """
                UPDATE user
                SET status = :status
                WHERE user_id IN (:userIds)
                """;
        final SqlParameterSource params = new MapSqlParameterSource()
               .addValue("status", status)
               .addValue("userIds", userIds);

        dbClient.sql(sql)
               .paramSource(params)
               .update();
    }

    @Override
    public void updateUserPassword(final Long userId, final String password) {
        final String sql = """
                UPDATE user
                SET password = :password
                WHERE user_id = :id
                """;
        final SqlParameterSource params = new MapSqlParameterSource()
               .addValue("id", userId)
               .addValue("password", password);

        dbClient.sql(sql)
               .paramSource(params)
               .update();
    }

    @Override
    public void updateUsersStatusByIds(final List<String> emails, final String aTrue) {
        final String sql = """
                UPDATE user
                SET status = :status
                WHERE email IN (:emails)
                """;
        final SqlParameterSource params = new MapSqlParameterSource()
               .addValue("status", aTrue)
               .addValue("email", emails);

        dbClient.sql(sql)
               .paramSource(params)
               .update();
    }
}
