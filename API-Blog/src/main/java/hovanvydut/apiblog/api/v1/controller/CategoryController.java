package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.CreateCategoryReq;
import hovanvydut.apiblog.api.v1.request.UpdateCategoryReq;
import hovanvydut.apiblog.api.v1.response.CategoryPageResp;
import hovanvydut.apiblog.api.v1.response.CategoryResp;
import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.core.category.CategoryService;
import hovanvydut.apiblog.core.category.dto.CategoryDTO;
import hovanvydut.apiblog.core.category.dto.CreateCategoryDTO;
import hovanvydut.apiblog.core.category.dto.UpdateCategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping("")
    public ResponseEntity<CategoryPageResp> getAllCategories(@RequestParam(required = false) Optional<String> keyword,
                                                             @RequestParam(required = false) Optional<Integer> page,
                                                             @RequestParam(required = false) Optional<Integer> size,
                                                             @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

        Page<CategoryDTO> pageCategoryDTOS = this.categoryService.getCategories(page.orElse(1),
                size.orElse(PagingConstant.CATEGORIES_PER_PAGE), sort, keyword.orElse(""));

        return ResponseEntity.ok(this.modelMapper.map(pageCategoryDTOS, CategoryPageResp.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResp> getCategory(@PathVariable("id") long id) {
        CategoryDTO dto = this.categoryService.getCategory(id);

        return ResponseEntity.ok(this.modelMapper.map(dto, CategoryResp.class));
    }

    @PostMapping("")
    public ResponseEntity<CategoryResp> createCategory(@Valid @RequestBody CreateCategoryReq req) {
        CreateCategoryDTO dto = this.modelMapper.map(req, CreateCategoryDTO.class);
        CategoryDTO categoryDTO = this.categoryService.createCategory(dto);

        return ResponseEntity.ok(this.modelMapper.map(categoryDTO, CategoryResp.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResp> updateCategory(@PathVariable("id") long id, @Valid @RequestBody UpdateCategoryReq req) {
        UpdateCategoryDTO dto = this.modelMapper.map(req,UpdateCategoryDTO.class);
        CategoryDTO categoryDTO = this.categoryService.updateCategory(id, dto);

        return ResponseEntity.ok(this.modelMapper.map(categoryDTO, CategoryResp.class));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategory(id);
    }

}
