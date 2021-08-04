package hovanvydut.apiblog.common.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PaginationParams {

    protected int page;
    protected int size;
    protected String[] sort;
    protected String keyword;

    public PaginationParams() {
        this.page = 1;
        this.size = 10;
        this.sort = null;
        this.keyword = "";
    }
}
