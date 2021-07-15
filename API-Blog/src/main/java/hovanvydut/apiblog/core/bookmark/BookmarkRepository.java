package hovanvydut.apiblog.core.bookmark;

import hovanvydut.apiblog.model.entity.BookmarkArticle;
import hovanvydut.apiblog.model.entity.BookmarkArticleId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/15/21
 */

@Repository
public interface BookmarkRepository extends PagingAndSortingRepository<BookmarkArticle, BookmarkArticleId> {

}
