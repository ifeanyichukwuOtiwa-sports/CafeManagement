package iwo.wintech.cafemanagement.service.impl;

import iwo.wintech.cafemanagement.dto.SignupDto;
import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.persistence.api.UserRepository;
import iwo.wintech.cafemanagement.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public String registerUser(final User user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
        if (userOpt.isPresent()) {
            return "User with this email already exists";
        }

        userRepository.save(user);

        return "User created successfully";
    }

    @Override
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
