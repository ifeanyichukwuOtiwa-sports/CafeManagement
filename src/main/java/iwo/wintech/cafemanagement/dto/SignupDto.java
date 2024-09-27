package iwo.wintech.cafemanagement.dto;

public record SignupDto(
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
