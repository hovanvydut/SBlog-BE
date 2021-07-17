package hovanvydut.apiblog.core.article;

import hovanvydut.apiblog.model.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    Optional<Article> findBySlug(String slug);
}
