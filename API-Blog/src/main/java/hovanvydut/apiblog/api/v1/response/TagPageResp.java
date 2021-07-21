package hovanvydut.apiblog.api.v1.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/2/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagPageResp {

    @ApiModelProperty(notes = "List of all Tags")
    private List<TagResp> content;

    @ApiModelProperty(notes = "Total items of all pages")
    private int totalElements;

    @ApiModelProperty(notes = "Total number of pages")
    private int totalPages;

    @ApiModelProperty(notes = "Number of items of each page")
    private int size;

    @ApiModelProperty(notes = "Zero based index of the current page")
    private int number;
}
