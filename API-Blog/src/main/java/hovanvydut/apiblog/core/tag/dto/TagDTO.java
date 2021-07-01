package hovanvydut.apiblog.core.tag.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

@Data
public class TagDTO {

    @Min(1)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    private String image;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime lastEditedAt;
}
