package iwo.wintech.cafemanagement.service.events;

public record UserNotificationEvent(
        Long userId,
        EmailMessageType message,
        String params) {
}
