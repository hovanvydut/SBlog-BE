package hovanvydut.apiblog.api.v1.category;

import hovanvydut.apiblog.api.v1.category.dto.*;
import hovanvydut.apiblog.core.category.CategoryService;
import hovanvydut.apiblog.core.category.dto.CategoryDTO;
import hovanvydut.apiblog.core.category.dto.CreateCategoryDTO;
import hovanvydut.apiblog.core.category.dto.UpdateCategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{slug}/articles")
    public void getAllArticlesOfCategory(@PathVariable String slug) {

    }

    @GetMapping("")
    public ResponseEntity<CategoryPageResp> getAllCategories(@Valid CategoryPaginationParams req) {

        Page<CategoryDTO> pageCategoryDTOS = this.categoryService.getCategories(req.getPage(),
                req.getSize(), req.getSort(), req.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(pageCategoryDTOS, CategoryPageResp.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResp> getCategory(@PathVariable("id") long id) {
        CategoryDTO dto = this.categoryService.getCategory(id);

        return ResponseEntity.ok(this.modelMapper.map(dto, CategoryResp.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<CategoryResp> createCategory(@Valid @RequestBody CreateCategoryReq req) {
        CreateCategoryDTO dto = this.modelMapper.map(req, CreateCategoryDTO.class);
        CategoryDTO categoryDTO = this.categoryService.createCategory(dto);

        return ResponseEntity.ok(this.modelMapper.map(categoryDTO, CategoryResp.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResp> updateCategory(@PathVariable("id") long id, @Valid @RequestBody UpdateCategoryReq req) {
        UpdateCategoryDTO dto = this.modelMapper.map(req,UpdateCategoryDTO.class);
        CategoryDTO categoryDTO = this.categoryService.updateCategory(id, dto);

        return ResponseEntity.ok(this.modelMapper.map(categoryDTO, CategoryResp.class));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategory(id);
    }

}
