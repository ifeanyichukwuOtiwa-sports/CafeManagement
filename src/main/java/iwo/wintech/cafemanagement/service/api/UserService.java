package iwo.wintech.cafemanagement.service.api;

import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.dto.UserInfoUpdateRequest;
import iwo.wintech.cafemanagement.entity.User;

import java.util.List;

public interface UserService {
    String registerUser(User request);

    User findByEmail(String email);

    List<UserDto> getAllUsers();

    Boolean updateInfo(UserDto user, UserInfoUpdateRequest request);

    void enableUsers(List<Long> userIds);

    List<UserDto> findUsers(List<Long> userIds);

    UserDto findById(Long userId);

    boolean checkToken(UserDto user);

    void updateUser(User withPassword);

    void updateUserPassword(Long userId, String password);
}
