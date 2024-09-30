package iwo.wintech.cafemanagement.persistence.api;

import iwo.wintech.cafemanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
    List<User> fetchByStatus(String status);
    List<User> getAllUsers();
    List<User> findAllUsersById(List<Long> userIds);
    List<User> getAllUsersByRole(String role);
    Optional<User> findById(Long id);
    void updateUser(User updateRequest);
    void updateUsersStatus(final List<Long> userIds, String status);
    void updateUserPassword(Long userId, String password);
    void updateUsersStatusByIds(List<String> userIds, String aTrue);
}
