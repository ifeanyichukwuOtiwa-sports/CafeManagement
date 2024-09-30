package iwo.wintech.cafemanagement.service.events;

import lombok.Builder;
import lombok.Getter;

@Builder
public record EmailMessage(
    String recipientEmail,
    String subject,
    String body
){
}
