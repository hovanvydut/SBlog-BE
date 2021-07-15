package hovanvydut.apiblog.api.v1.request;

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
    private int page;
    private int size;
    private String[] sort;
    private String keyword;

    public PaginationParams() {
        this.page = 1;
        this.size = 10;
        this.sort = new String[] {"id,asc"};
        this.keyword = "";
    }
}
