package hovanvydut.apiblog.api.v1.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.common.request.PaginationParams;
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
public class CategoryPaginationParams extends PaginationParams {

    public CategoryPaginationParams() {
        super();
        this.size = PagingConstant.CATEGORIES_PER_PAGE;
    }

}
