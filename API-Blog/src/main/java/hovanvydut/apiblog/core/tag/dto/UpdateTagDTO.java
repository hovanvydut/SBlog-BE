package hovanvydut.apiblog.core.tag.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

@Data
@Accessors(chain = true)
public class UpdateTagDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotNull
    private LocalDateTime lastEditedAt;
}
