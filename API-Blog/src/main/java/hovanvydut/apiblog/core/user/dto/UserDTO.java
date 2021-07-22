package hovanvydut.apiblog.core.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.common.enums.GenderEnum;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private boolean enabled;
    private LocalDate birthday;
    private GenderEnum gender;
    private String biography;
}
