package hovanvydut.apiblog.core.article;

import hovanvydut.apiblog.model.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Article findBySlug(String slug);
}
