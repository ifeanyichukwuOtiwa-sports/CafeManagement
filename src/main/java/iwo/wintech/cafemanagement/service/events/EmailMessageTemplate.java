package iwo.wintech.cafemanagement.service.events;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class EmailMessageTemplate {

    public static final Map<EmailMessageType, BaseMessage> MESSAGES = Map.ofEntries(
            Map.entry(
                    EmailMessageType.USER_UPDATED,
                    new BaseMessage("Great news! Your account has been successfully updated. If you didn't make these changes, please contact our support team immediately.",
                            EmailMessageType.USER_UPDATED.name()
                    )
            ),
            Map.entry(
                    EmailMessageType.PASSWORD_RESET,
                    new BaseMessage(
                            "Your password has been reset to %s. If you didn't request this change, please secure your account and contact us right away.",
                            EmailMessageType.PASSWORD_RESET.name()
                    )
            ),
            Map.entry(
                    EmailMessageType.REGISTRATION_CONFIRMED,
                    new BaseMessage("Welcome aboard! Your registration is confirmed. We're excited to have you as part of our community.",
                    EmailMessageType.REGISTRATION_CONFIRMED.name()
                    )
            )
    );

    // I could make this more dynamic by creating a yaml file that has template type and message. let's see how this go.

    public static BaseMessage getMessage(final EmailMessageType type) {
        return MESSAGES.getOrDefault(type, null);
    }
}
