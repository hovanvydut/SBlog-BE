package hovanvydut.apiblog.core.tag.repository;

import hovanvydut.apiblog.model.entity.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
}
