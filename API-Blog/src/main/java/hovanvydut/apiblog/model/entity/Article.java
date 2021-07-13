package hovanvydut.apiblog.model.entity;

import hovanvydut.apiblog.common.constant.ArticleScopeEnum;
import hovanvydut.apiblog.common.constant.ArticleStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/10/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "article", indexes = @Index(columnList = "status"))
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "slug", unique = true, nullable = false, length = 255)
    private String slug;

    @Column(name = "transliterated", nullable = false, length = 255)
    private String transliterated;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail", nullable = true, length = 255)
    private String thumbnail;

    @Column(name = "scope", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private ArticleScopeEnum scope;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private ArticleStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "article")
    private Set<ArticleVote> votes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

//    @PreUpdate
//    protected void onUpdate() {
//        lastUpdatedAt = LocalDateTime.now();
//    }
}
