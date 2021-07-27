package hovanvydut.apiblog.api.v1.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
public class CommentArticleResp {
    private long id;
    private String content;
    private String imageSlug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserCommentResp fromUser;
}

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class UserCommentResp {
    private String username;
    private String fullName;
}

