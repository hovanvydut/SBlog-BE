package com.debugbybrain.blog.core.article.dto;

import com.debugbybrain.blog.core.category.dto.CategoryDTO;
import com.debugbybrain.blog.core.tag.dto.TagDTO;
import com.debugbybrain.blog.core.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 9/3/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class SeriesDTO extends ArticleDTO {
    private Set<ArticleDTO> articles;

    @Getter
    @Setter
    @NoArgsConstructor
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    public static class ArticleDTO {
        private String title;
        private String slug;
        private String transliterated;
    }
}
