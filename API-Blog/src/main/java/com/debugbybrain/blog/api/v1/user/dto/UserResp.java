package com.debugbybrain.blog.api.v1.user.dto;

import com.debugbybrain.blog.entity.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class UserResp {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
    private boolean enabled;
    private LocalDate birthday;
    private GenderEnum gender;
    private String biography;
}
