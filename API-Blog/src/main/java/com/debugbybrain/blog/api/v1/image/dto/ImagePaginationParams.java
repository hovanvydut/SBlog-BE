package com.debugbybrain.blog.api.v1.image.dto;

import com.debugbybrain.blog.common.constant.PagingConstant;
import com.debugbybrain.blog.common.request.PaginationParams;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class ImagePaginationParams extends PaginationParams {

    public ImagePaginationParams() {
        super();
        this.size = PagingConstant.ARTICLE_PER_PAGE;
    }

}
