package iwo.wintech.cafemanagement.persistence.impl;

import iwo.wintech.cafemanagement.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.sql.ResultSet;
import java.sql.SQLException;


@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityMapper {

    public static User mapRow(final ResultSet r, final int ignored) throws SQLException {
        return User.builder()
                .userId(r.getLong("user_id"))
                .role(r.getString("role"))
                .status(r.getString("status"))
                .phoneNumber(r.getString("phone_number"))
                .email(r.getString("email"))
                .password(r.getString("password"))
                .firstName(r.getString("first_name"))
                .lastName(r.getString("last_name"))
                .build();
    }
}
