package hovanvydut.apiblog.core.tag.repository;

import hovanvydut.apiblog.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    Tag findByName(String name);

    Tag findBySlug(String slug);

    Tag findByNameOrSlug(String name, String slug);

    @Query("SELECT t FROM Tag t WHERE t.name LIKE %?1%")
    Page<Tag> search(String keyword, Pageable pageable);

}
