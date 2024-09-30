package iwo.wintech.cafemanagement.rest.admin;

public record AdminRegisterRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {
}
