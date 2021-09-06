package com.debugbybrain.blog.core.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UpdateArticleDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Size(min = 1, max = 5)
    private Set<Long> tagIds;

    @URL
    private String thumbnail;

    @NotNull
    @Min(1)
    private long categoryId;

    public void setTitle(String title) {
        this.title = title.trim().replaceAll("\\s{2,}", " ");
    }

}
