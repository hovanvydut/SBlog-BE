package com.debugbybrain.blog.api.v1.article.dto;

/**
 * @author hovanvydut
 * Created on 9/3/21
 */


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
public class SeriesResp {
    private String title;
    private String slug;
    private String transliterated;
    private String content;
    private String thumbnail;
    private UserResp author;
    private Set<TagResp> tags;
    private CategoryResp category;
    private String lastUpdatedAt;
    private String publishedAt;
    private String deletedAt;
    private int viewCount = 10;
    private int commentCount = 3;
    private int bookmarkCount = 8;
    private int voteCount = 35;
}