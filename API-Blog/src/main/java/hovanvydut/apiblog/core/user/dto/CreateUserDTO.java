package hovanvydut.apiblog.core.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.common.enums.GenderEnum;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CreateUserDTO {

    // TODO: strictly validate fullName
    @NotBlank
    private String fullName;

    // TODO: strictly validate email
    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 3, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9]+([a-zA-Z0-9]{2,}|_[a-zA-Z0-9]+|-[a-zA-Z0-9]+|\\.[a-zA-Z0-9]+)+$")
    private String username;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;

    private LocalDate birthday;

    private GenderEnum gender;

    private String biography;
}
