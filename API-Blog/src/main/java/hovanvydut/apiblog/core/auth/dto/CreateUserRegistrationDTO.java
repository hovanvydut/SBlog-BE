package hovanvydut.apiblog.core.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class CreateUserRegistrationDTO {
    @NotBlank
    private String fullName;

    // TODO: consistent with RegistrationReq class
    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+([a-zA-Z0-9]{2,}|_[a-zA-Z0-9]+|-[a-zA-Z0-9]+|\\.[a-zA-Z0-9]+)+$")
    @Length(min = 3, max = 32)
    private String username;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;
}
