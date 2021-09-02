package com.debugbybrain.blog.api.v1.category;

import com.debugbybrain.blog.api.v1.category.dto.*;
import com.debugbybrain.blog.core.category.CategoryService;
import com.debugbybrain.blog.core.category.dto.CategoryDTO;
import com.debugbybrain.blog.core.category.dto.CreateCategoryDTO;
import com.debugbybrain.blog.core.category.dto.UpdateCategoryDTO;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Filter articles by category")
    @GetMapping("/{slug}/articles")
    public void getAllArticlesOfCategory(@PathVariable String slug) {

    }

    @ApiOperation(value = "Get all categories")
    @GetMapping("/categories")
    public ResponseEntity<CategoryPageResp> getAllCategories(@Valid CategoryPaginationParams params) {
        Page<CategoryDTO> pageCategoryDTOS = this.categoryService.getCategories(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(pageCategoryDTOS, CategoryPageResp.class));
    }

    @ApiOperation(value = "Get category by id")
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResp> getCategory(@PathVariable("id") long id) {
        CategoryDTO dto = this.categoryService.getCategory(id);

        return ResponseEntity.ok(this.modelMapper.map(dto, CategoryResp.class));
    }

    @ApiOperation(value = "Create a new category")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<CategoryResp> createCategory(@Valid @RequestBody CreateCategoryReq req) {
        CreateCategoryDTO dto = this.modelMapper.map(req, CreateCategoryDTO.class);
        CategoryDTO categoryDTO = this.categoryService.createCategory(dto);

        return ResponseEntity.ok(this.modelMapper.map(categoryDTO, CategoryResp.class));
    }

    @ApiOperation(value = "Uploading image for the category")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories/{id}/upload-image")
    public String uploadImage(@PathVariable long id, @RequestParam("image") MultipartFile multipartFile) {

        return null;
    }

    @ApiOperation(value = "Update the category by id")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/categories/{id}")
    public ResponseEntity<CategoryResp> updateCategory(@PathVariable("id") long id, @Valid @RequestBody UpdateCategoryReq req) {
        UpdateCategoryDTO dto = this.modelMapper.map(req,UpdateCategoryDTO.class);
        CategoryDTO categoryDTO = this.categoryService.updateCategory(id, dto);

        return ResponseEntity.ok(this.modelMapper.map(categoryDTO, CategoryResp.class));
    }

    @ApiOperation(value = "Delete category by id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategory(id);
    }

}
