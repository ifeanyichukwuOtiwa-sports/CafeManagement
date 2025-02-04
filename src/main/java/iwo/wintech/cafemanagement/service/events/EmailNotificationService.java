package iwo.wintech.cafemanagement.service.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EmailNotificationService {
    private final JavaMailSender mailSender;

    private void sendNotification(final EmailMessage message) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(message.subject());
        mailMessage.setText(message.body());
        mailMessage.setTo(message.recipientEmail());

        mailSender.send(mailMessage);

    }

    @EventListener(UserNotificationEvent.class)
    public void handleUserNotificationEvent(UserNotificationEvent event) {
        final String email = event.email();

        final EmailMessageType messageType = event.message();
        final BaseMessage message = EmailMessageTemplate.getMessage(messageType);
        final String messageParams = event.params();

        sendNotification(
                new EmailMessage(
                        email,
                        message.subject(),
                        messageParams == null ? message.message() : String.format(message.message(), messageParams)
                )
        );
    }
}