package hovanvydut.apiblog.core.category;

import hovanvydut.apiblog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Page<Category> findByNameLike(String name, Pageable pageable);
    Optional<Category> findByName(String name);
    Optional<Category> findBySlug(String slug);
}
