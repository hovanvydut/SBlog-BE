package hovanvydut.apiblog.api.v1.tag.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.common.regex.TagRegex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTagReq {

    @Pattern(regexp = TagRegex.name.pattern, message = TagRegex.name.message)
    private String name;

    private String description;

    @Pattern(regexp = TagRegex.slug.pattern, message = TagRegex.slug.message)
    private String slug;

    @URL
    private String image;

}
