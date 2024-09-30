package iwo.wintech.cafemanagement.dto;

import javax.annotation.Nullable;

public record UserInfoUpdateRequest(
        String firstName,
        String lastName,
        @Nullable
        String phoneNumber
) {
}
