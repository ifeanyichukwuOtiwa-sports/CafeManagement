package iwo.wintech.cafemanagement.entity.category;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@With
@EqualsAndHashCode
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = -796857742649528601L;


    private Long id;
    private String name;
    private String description;

}
