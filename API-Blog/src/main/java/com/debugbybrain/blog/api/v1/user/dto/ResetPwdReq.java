package com.debugbybrain.blog.api.v1.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ResetPwdReq {

    // TODO: check min, max appropriate with database
    @NotNull
    @Length(min = 8, max = 32)
    private String oldPassword;

    @NotNull
    @Length(min = 8, max = 32)
    private String newPassword;

}
