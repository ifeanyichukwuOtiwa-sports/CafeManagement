package iwo.wintech.cafemanagement.dto;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String status
) {
}
