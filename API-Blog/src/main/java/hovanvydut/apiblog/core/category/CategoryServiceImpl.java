package hovanvydut.apiblog.core.category;

import hovanvydut.apiblog.common.exception.CategoryNotFoundException;
import hovanvydut.apiblog.common.exception.MyError;
import hovanvydut.apiblog.common.exception.MyRuntimeException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.category.dto.CategoryDTO;
import hovanvydut.apiblog.core.category.dto.CreateCategoryDTO;
import hovanvydut.apiblog.core.category.dto.UpdateCategoryDTO;
import hovanvydut.apiblog.model.entity.Category;
import hovanvydut.apiblog.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 7/5/21
 */

@Validated
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CategoryDTO> getCategories(int page, int size, String[] sort, String searchKeyword) {
        Sort sortObj = SortAndPaginationUtil.processSort(sort);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);

        Page<Category> pageCategories;

        if (searchKeyword == null || searchKeyword.isBlank()) {
            pageCategories = this.categoryRepo.findAll(pageable);
        } else {
            pageCategories = this.categoryRepo.findByNameLike(searchKeyword, pageable);
        }

        List<Category> categories = pageCategories.getContent();
        List<CategoryDTO> categoryDTOs = this.modelMapper.map(categories, new TypeToken<List<Tag>>() {}.getType());

        return new PageImpl<>(categoryDTOs, pageable, pageCategories.getTotalElements());
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO createCategory(@Valid CreateCategoryDTO dto) {
        // check name and slug is unique
        List<MyError> errors = checkUnique(dto);
        if (errors.size() > 0) {
            throw new MyRuntimeException(errors);
        }

        Category category = this.modelMapper.map(dto, Category.class);
        if (category.getSlug() == null) {
            category.setSlug(SlugUtil.slugify(category.getName()));
        }

        Category savedCategory = this.categoryRepo.save(category);

        return this.modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto) {

        // check name and slug is unique
        List<MyError> errors = checkUnique(id, dto);
        if (errors.size() > 0) {
            throw new MyRuntimeException(errors);
        }

        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        this.modelMapper.map(dto, category);

        if (category.getSlug() == null) {
            category.setSlug(SlugUtil.slugify(category.getName()));
        }

        Category savedCategory = this.categoryRepo.save(category);

        return this.modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        this.categoryRepo.delete(category);
    }

    private List<MyError> checkUnique(CreateCategoryDTO dto) {
        List<MyError> errorList = new ArrayList<>();

        this.categoryRepo.findByName(dto.getName())
                .ifPresent(category -> errorList.add(new MyError().setSource("name").setMessage("The name has already been taken")));

        this.categoryRepo.findBySlug(dto.getSlug())
                .ifPresent(category -> errorList.add(new MyError().setSource("slug").setMessage("The slug has already been taken")));

        return errorList;
    }
    private List<MyError> checkUnique(Long id, UpdateCategoryDTO dto) {
        List<MyError> errorList = new ArrayList<>();

        this.categoryRepo.findByName(dto.getName()).ifPresent(category -> {
            if (category.getId() != id) {
                errorList.add(new MyError().setSource("name").setMessage("The name has already been taken"));
            }
        });

        this.categoryRepo.findBySlug(dto.getSlug()).ifPresent(category -> {
            if (category.getId() != id) {
                errorList.add(new MyError().setSource("slug").setMessage("The slug has already been taken"));
            }
        });

        return errorList;
    }
}
