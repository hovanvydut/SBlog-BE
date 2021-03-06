package com.debugbybrain.blog.api.v1.vote.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.debugbybrain.blog.entity.enums.ArticleVoteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleVoteReq {
    private ArticleVoteType type;
}
