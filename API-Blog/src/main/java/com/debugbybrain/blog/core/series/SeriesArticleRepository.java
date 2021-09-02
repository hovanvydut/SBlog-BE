package com.debugbybrain.blog.core.series;

import com.debugbybrain.blog.entity.SeriesArticle;
import com.debugbybrain.blog.entity.SeriesArticleId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author hovanvydut
 * Created on 8/29/21
 */

public interface SeriesArticleRepository extends PagingAndSortingRepository<SeriesArticle, SeriesArticleId> {

}
