package iwo.wintech.cafemanagement.persistence.api;

import iwo.wintech.cafemanagement.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
}
