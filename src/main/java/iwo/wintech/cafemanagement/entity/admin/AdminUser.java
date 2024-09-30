package iwo.wintech.cafemanagement.entity.admin;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder
@Getter
@With
@EqualsAndHashCode(of = {"id", "email" })
public class AdminUser {
    private Long id;
    private Long roleId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
