package hovanvydut.apiblog.api.v1.tag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
public class CreateTagReq {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\s[a-zA-Z0-9]+)*$", message = "Only include character a-z,A-Z,0-9 and space between words")
    private String name;

    private String description;

    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Only include character a-z,0-9 and - between words")
    private String slug;

    private String image;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

}
