package hovanvydut.apiblog.api.v1.image.dto;

import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.common.request.PaginationParams;

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
