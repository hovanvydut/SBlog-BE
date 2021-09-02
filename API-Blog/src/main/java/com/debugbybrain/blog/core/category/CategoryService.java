package com.debugbybrain.blog.core.category;

import com.debugbybrain.blog.core.category.dto.CategoryDTO;
import com.debugbybrain.blog.core.category.dto.CreateCategoryDTO;
import com.debugbybrain.blog.core.category.dto.UpdateCategoryDTO;
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
    CategoryDTO updateCategory(Long id, @Valid UpdateCategoryDTO dto);
    void deleteCategory(long id);
}
