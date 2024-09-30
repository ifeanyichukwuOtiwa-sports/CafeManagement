package iwo.wintech.cafemanagement.service.impl;

import iwo.wintech.cafemanagement.dto.AdminDto;
import iwo.wintech.cafemanagement.dto.UserDto;
import iwo.wintech.cafemanagement.dto.UserInfoUpdateRequest;
import iwo.wintech.cafemanagement.entity.User;
import iwo.wintech.cafemanagement.exception.ErrorCode;
import iwo.wintech.cafemanagement.persistence.api.UserRepository;
import iwo.wintech.cafemanagement.service.api.UserService;
import iwo.wintech.cafemanagement.service.events.EmailMessageType;
import iwo.wintech.cafemanagement.service.events.UserNotificationEvent;
import iwo.wintech.cafemanagement.service.mapper.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Converter converter;
    private final ApplicationEventPublisher emailNotificationPublisher;

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

    @Override
    public List<UserDto> getAllUsers(final AdminDto admin) {
        final List<User> allUsers = userRepository.getAllUsers();
        return allUsers.stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public Boolean updateInfo(final UserDto user, final UserInfoUpdateRequest request) {
        final User updateRequest = User.builder()
                .userId(user.id())
                .firstName(Optional.ofNullable(request.firstName()).orElse(user.firstName()))
                .lastName(Optional.ofNullable(request.lastName()).orElse(user.lastName()))
                .phoneNumber(Optional.ofNullable(request.phoneNumber()).orElse(user.phoneNumber()))
                .build();
        userRepository.updateUser(updateRequest);
        return Boolean.TRUE;
    }

    @Override
    public void enableUsers(final List<String> emails, final AdminDto admin) {
        if (emails.isEmpty()){
            log.warn("No emails");
            return;
        }
        validateUsersEmail(emails);
        userRepository.updateUsersStatusByIds(emails, "true");
        emails.forEach(id -> sendNotificationToUsers(new UserNotificationEvent(id, EmailMessageType.REGISTRATION_CONFIRMED, null)));

    }

    @Override
    public List<UserDto> findUsers(final List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        return userRepository.findAllUsersById(userIds)
                .stream()
                .map(converter::convert)
                .toList();
    }

    @Override
    public UserDto findById(final Long userId) {
        return userRepository.findById(userId)
                .map(converter::convert)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.requestException("Could not find User"));
    }

    @Override
    public boolean checkToken(final UserDto user) {
        return true;
    }

    @Override
    public void updateUser(final User withPassword) {
        userRepository.save(withPassword);
    }

    @Override
    public void updateUserPassword(final Long userId, final String password) {
        userRepository.updateUserPassword(userId, password);
    }

    private void sendNotificationToUsers(final UserNotificationEvent userNotificationEvent) {
        emailNotificationPublisher.publishEvent(userNotificationEvent);
    }

    @SuppressWarnings("all")
    private void validateUsers(final List<Long> userIds) {
        // Todo: left Empty for now
    }

    @SuppressWarnings("all")
    private void validateUsersEmail(final List<String> emails) {
        // Todo: left Empty for now
    }
}
