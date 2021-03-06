package com.debugbybrain.blog.api.v1.auth.dto;

import com.debugbybrain.blog.common.annotations.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class RegistrationReq {

    @NotBlank
    private String fullName;

    @NotBlank
    @ValidEmail
    private String email;

    // TODO: Create custom annotation for username
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+([a-zA-Z0-9]{2,}|_[a-zA-Z0-9]+|-[a-zA-Z0-9]+|\\.[a-zA-Z0-9]+)+$")
    @Length(min = 3, max = 32)
    private String username;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;

}
