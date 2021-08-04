package hovanvydut.apiblog.api.v1.user.dto;

import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.common.request.PaginationParams;

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
