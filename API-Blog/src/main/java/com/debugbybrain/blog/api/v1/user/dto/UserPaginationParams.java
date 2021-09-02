package com.debugbybrain.blog.api.v1.user.dto;

import com.debugbybrain.blog.common.constant.PagingConstant;
import com.debugbybrain.blog.common.request.PaginationParams;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class UserPaginationParams extends PaginationParams {

    public UserPaginationParams() {
        super();
        this.size = PagingConstant.USERS_PER_PAGE;
    }

}
