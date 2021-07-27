package hovanvydut.apiblog.api.v1.article.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class UpdateArticleReq {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Size(min = 1, max = 5)
    private Set<Long> tagIds;

    @NotNull
    @Min(1)
    private long categoryId;

    @URL
    private String thumbnail;

    public void setTitle(String title) {
        this.title = title.trim().replaceAll("\\s{2,}", " ");
    }
}