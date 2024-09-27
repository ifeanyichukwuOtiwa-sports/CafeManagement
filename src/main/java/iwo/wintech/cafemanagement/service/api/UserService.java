package iwo.wintech.cafemanagement.service.api;

import iwo.wintech.cafemanagement.entity.User;

public interface UserService {
    String registerUser(User request);

    User findByEmail(String email);
}
