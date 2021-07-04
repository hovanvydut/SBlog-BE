package hovanvydut.apiblog.core.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

    @Email
    private String email;

    @Length(min = 3, max = 32)
    private String username;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;
}
