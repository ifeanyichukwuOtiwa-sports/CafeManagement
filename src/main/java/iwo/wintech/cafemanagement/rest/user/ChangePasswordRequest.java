package iwo.wintech.cafemanagement.rest.user;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword,
        String confirmPassword
) {
    public boolean isPasswordValid() {
        return newPassword.equals(confirmPassword);
    }

    public boolean newPasswordMatchesOldPassword() {
        return this.currentPassword.equals(newPassword);
    }
}
