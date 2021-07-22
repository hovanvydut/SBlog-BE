package hovanvydut.apiblog.core.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CommentDTO {
    private long id;
    private String content;
    private String imageSlug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO fromUser;

    public CommentDTO(long id, String content, String imageSlug, LocalDateTime createdAt, LocalDateTime updatedAt, String fullName, String username) {
        this.id = id;
        this.content = content;
        this.imageSlug = imageSlug;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fromUser = new UserDTO().setFullName(fullName).setUsername(username);
    }
}
