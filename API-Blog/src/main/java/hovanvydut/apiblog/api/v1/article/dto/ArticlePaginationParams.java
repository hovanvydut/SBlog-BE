package hovanvydut.apiblog.api.v1.article.dto;

import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.common.request.PaginationParams;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class ArticlePaginationParams extends PaginationParams {

    public ArticlePaginationParams() {
        super();
        this.size = PagingConstant.ARTICLE_PER_PAGE;
    }

}
