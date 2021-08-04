package hovanvydut.apiblog.api.v1.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class UpdateCategoryReq {
    @Length(min = 1, max = 255)
    private String name;

    private String description;

    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$")
    private String slug;

    @URL
    private String image;
}
