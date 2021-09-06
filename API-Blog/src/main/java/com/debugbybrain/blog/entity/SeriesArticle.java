package com.debugbybrain.blog.entity;

import com.debugbybrain.blog.entity.enums.SeriesArticleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 8/15/21
 */

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "series_article")
public class SeriesArticle {

    @EmbeddedId
    private SeriesArticleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("seriesId")
    @JoinColumn(name = "series_id", nullable = false)
    private Article series;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("articleId")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "status", nullable = false)
    private SeriesArticleStatus status = SeriesArticleStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
