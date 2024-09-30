package iwo.wintech.cafemanagement.service.events;

public record UserNotificationEvent(
        String email,
        EmailMessageType message,
        String params) {
}
