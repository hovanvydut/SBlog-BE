package com.debugbybrain.blog.core.user.dto;

import com.debugbybrain.blog.entity.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String avatar;
    private LocalDateTime createdAt;
    private boolean enabled;
    private LocalDate birthday;
    private GenderEnum gender;
    private String biography;
}
