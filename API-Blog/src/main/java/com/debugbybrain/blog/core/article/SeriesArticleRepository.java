package com.debugbybrain.blog.core.article;

import com.debugbybrain.blog.entity.SeriesArticle;
import com.debugbybrain.blog.entity.SeriesArticleId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author hovanvydut
 * Created on 9/3/21
 */

public interface SeriesArticleRepository extends PagingAndSortingRepository<SeriesArticle, SeriesArticleId> {

}
