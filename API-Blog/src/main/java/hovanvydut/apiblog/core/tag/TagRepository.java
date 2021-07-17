package hovanvydut.apiblog.core.tag;

import hovanvydut.apiblog.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    Optional<Tag> findBySlug(String slug);

    Optional<Tag> findByNameOrSlug(String name, String slug);

    @Query("SELECT t FROM Tag t WHERE t.name LIKE %?1%")
    Page<Tag> search(String keyword, Pageable pageable);

}
