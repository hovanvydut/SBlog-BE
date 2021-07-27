package hovanvydut.apiblog.api.v1.tag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

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

    @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\s[a-zA-Z0-9]+)*$")
    private String name;

    private String description;

    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$")
    private String slug;

    @URL
    private String image;

    @JsonIgnore
    private LocalDateTime lastEditedAt = LocalDateTime.now();
}
