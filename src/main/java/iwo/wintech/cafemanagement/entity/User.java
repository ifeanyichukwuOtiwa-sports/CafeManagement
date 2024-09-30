package iwo.wintech.cafemanagement.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@With
@EqualsAndHashCode(of = {"userId", "email" })
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String status;
    private String role;


    public String getMaskedUserPassword() {
        return String.format("%s", this.getPassword().substring(0, 3));
    }

}
