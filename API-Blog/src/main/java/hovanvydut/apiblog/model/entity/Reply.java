package hovanvydut.apiblog.model.entity;

import hovanvydut.apiblog.common.enums.ReplyTypeEnum;
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
@Table(name = "reply", indexes = @Index(columnList = "reply_type"))
@Check(constraints = "(content IS NOT NULL OR image_slug IS NOT NULL) AND (reply_type <> 1 OR parent_reply_id IS NOT NULL)")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "image_slug", length = 255)
    private String imageSlug;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_id", nullable = true)
    private Reply parentReply;

    @Column(name = "reply_type", nullable = false)
    private ReplyTypeEnum replyType = ReplyTypeEnum.COMMENT;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
