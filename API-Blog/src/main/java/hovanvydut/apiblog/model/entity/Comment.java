package hovanvydut.apiblog.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/20/21
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "comment")
@Check(constraints = "content IS NOT NULL OR image_slug IS NOT NULL")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "image_slug", length = 255)
    private String imageSlug;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
