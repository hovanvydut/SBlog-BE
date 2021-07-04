package hovanvydut.apiblog.core.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UserRegistrationDTO {

    @Min(0)
    private Long id;

    @NotBlank
    @Length(min = 1, max = 32)
    private String fullName;

    @Email
    private String email;

    @Pattern(regexp = "[a-zA-Z0-9]{3,32}")
    private String username;

    @NotNull
    private LocalDateTime createdAt;
}
