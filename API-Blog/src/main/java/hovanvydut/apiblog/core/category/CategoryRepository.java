package hovanvydut.apiblog.core.category;

import hovanvydut.apiblog.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Page<Category> findByNameLike(String name, Pageable pageable);
    Category findByName(String name);
    Category findBySlug(String slug);
}
