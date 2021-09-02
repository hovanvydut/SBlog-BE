package com.debugbybrain.blog.core.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CreateReplytDTO {

    @Length(min = 0, max = 5000)
    private String content;

    @Length(min = 0, max = 255)
    private String imageSlug;

    @AssertTrue(message = "content or imageSlug is required")
    private boolean isContentOrImageSlugNotNull() {
        return this.content != null || this.imageSlug != null;
    }

}
