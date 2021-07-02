package hovanvydut.apiblog.core.tag.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
/**
 * @author hovanvydut
 * Created on 7/1/21
 */

// NOTE: validate seriously
@Data
public class CreateTagDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotNull
    private LocalDateTime createdAt;

}
