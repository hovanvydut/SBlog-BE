package com.debugbybrain.blog.api.v1.user.dto;

import com.debugbybrain.blog.entity.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class UpdateUserReq {

    //FIXME: Add validator for vietnamese character and at least 1 character, not blank
    @Length(min = 1, max = 32)
    private String fullName;

    private LocalDate birthday;

    private GenderEnum gender;

    private String biography;

}
