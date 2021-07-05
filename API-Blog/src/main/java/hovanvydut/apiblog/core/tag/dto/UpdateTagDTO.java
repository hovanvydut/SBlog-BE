package hovanvydut.apiblog.core.tag.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

@Data
@Accessors(chain = true)
public class UpdateTagDTO {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\s[a-zA-Z0-9]+)*$", message = "Only include character a-z,A-Z,0-9 and space between words")
    private String name;

    private String description;

    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Only include character a-z,0-9 and - between words")
    private String slug;

    @URL
    private String image;

    @NotNull
    private LocalDateTime lastEditedAt;
}
