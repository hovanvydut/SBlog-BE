package hovanvydut.apiblog.api.v1.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hovanvydut.apiblog.core.category.dto.CategoryDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ArticleResp {
    private String title;
    private String slug;
    private String transliterated;
    private String content;
    private String thumbnail;
    private UserDTO author;
    private Set<TagDTO> tags;
    private CategoryDTO category;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime deletedAt;
    private int viewCount = 10;
    private int commentCount = 3;
    private int bookmarkCount = 8;
    private int voteCount = 35;
}
