package com.debugbybrain.blog.api.v1.category.dto;

import com.debugbybrain.blog.common.regex.CategoryRegex;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CreateCategoryReq {

    @NotBlank
    @Length(min = 1, max = 255)
    private String name;

    private String description;

    @Pattern(regexp = CategoryRegex.slug.pattern,message = CategoryRegex.slug.message)
    private String slug;

    public void setName(String name) {
        this.name = name.replaceAll("\\s{2,}", " ").trim();
    }

}
