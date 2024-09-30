package iwo.wintech.cafemanagement.dto;

public record AdminDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        Long roleId
) {
}
