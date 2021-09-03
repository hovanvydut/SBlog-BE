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

import java.time.LocalDateTime;
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
public class ArticleDTO {
    private String title;
    private String slug;
    private String transliterated;
    private String content;
    private String thumbnail;
    private UserDTO author;
    private Set<TagDTO> tags;
    private CategoryDTO category;
    private String lastUpdatedAt;
    private String publishedAt;
    private String deletedAt;
    private int viewCount = 10;
    private int commentCount = 3;
    private int bookmarkCount = 8;
    private int voteCount = 35;
}
