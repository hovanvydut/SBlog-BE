package com.debugbybrain.blog.api.v1.article.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/2/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleResp {
    private String title;
    private String slug;
    private String transliterated;
    private String content;
    private String thumbnail;
    private UserResp author;
    private Set<TagResp> tags;
    private CategoryResp category;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime deletedAt;
    private int viewCount = 10;
    private int commentCount = 3;
    private int bookmarkCount = 8;
    private int voteCount = 35;
}


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
class UserResp {
    private Long id;
    private String fullName;
    private String username;
}

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class TagResp {
    private long id;
    private String name;
    private String slug;
    private String image;
}

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
class CategoryResp {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String image;
}
