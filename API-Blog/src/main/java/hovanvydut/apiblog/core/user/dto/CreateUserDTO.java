package hovanvydut.apiblog.core.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.common.constant.GenderEnum;
import lombok.*;
import lombok.experimental.Accessors;

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
    private String fullName;
    private String email;
    private String username;
    private String password;
    private LocalDate birthday;
    private GenderEnum gender;
}
