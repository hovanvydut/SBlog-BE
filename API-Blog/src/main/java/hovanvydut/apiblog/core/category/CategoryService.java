package hovanvydut.apiblog.core.category;

import hovanvydut.apiblog.core.category.dto.CategoryDTO;
import hovanvydut.apiblog.core.category.dto.CreateCategoryDTO;
import hovanvydut.apiblog.core.category.dto.UpdateCategoryDTO;
import org.springframework.data.domain.Page;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

public interface CategoryService {
    Page<CategoryDTO> getCategories(int page, int size, String[] sort, String searchKeyword);
    CategoryDTO getCategory(Long id);
    CategoryDTO createCategory(@Valid CreateCategoryDTO dto);
    CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto);
    void deleteCategory(long id);
}
