package hovanvydut.apiblog.core.comment.dto;

import hovanvydut.apiblog.core.user.dto.UserDTO;

import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

public class CommentDTO {
    private long id;
    private String content;
    private String imageSlug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO userDTO;
}
