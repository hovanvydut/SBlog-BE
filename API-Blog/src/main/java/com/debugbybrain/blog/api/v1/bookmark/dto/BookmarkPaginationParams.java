package com.debugbybrain.blog.api.v1.bookmark.dto;

import com.debugbybrain.blog.common.constant.PagingConstant;
import com.debugbybrain.blog.common.request.PaginationParams;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author hovanvydut
 * Created on 7/17/21
 */

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class BookmarkPaginationParams extends PaginationParams {

    public BookmarkPaginationParams() {
        super();
        this.size = PagingConstant.BOOKMARK_PER_PAGE;
    }

}
