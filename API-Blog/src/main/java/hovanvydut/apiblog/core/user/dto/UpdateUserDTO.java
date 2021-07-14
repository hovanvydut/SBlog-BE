package hovanvydut.apiblog.core.user.dto;

import hovanvydut.apiblog.common.constant.GenderEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * @author hovanvydut
 * Created on 7/11/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UpdateUserDTO {

    @Length(min = 1, max = 32)
    private String fullName;

    private LocalDate birthday;

    private GenderEnum gender;

    private String biography;

}
