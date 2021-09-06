package com.debugbybrain.blog.api.v1.article.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * Created on 7/12/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CreateArticleReq {

    @NotBlank
    protected String title;

    @NotBlank
    protected String content;

    @NotNull
    @Size(min = 1, max = 5)
    protected Set<Long> tagIds;

    @NotNull
    @Min(1)
    protected long categoryId;

    @URL
    protected String thumbnail;

    public void setTitle(String title) {
        this.title = title.trim().replaceAll("\\s{2,}", " ");
    }

    public void setContent(String content) {
        this.content = content.trim();
    }

}
