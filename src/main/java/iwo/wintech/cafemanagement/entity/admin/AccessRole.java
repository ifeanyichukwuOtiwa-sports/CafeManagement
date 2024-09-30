package iwo.wintech.cafemanagement.entity.admin;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

@Builder
@Getter
@With
@EqualsAndHashCode(of = {"id", "name" })
public class AccessRole {
    private Long id;
    private String name;
}
