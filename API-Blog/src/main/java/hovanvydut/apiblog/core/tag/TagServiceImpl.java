package hovanvydut.apiblog.core.tag;

import hovanvydut.apiblog.common.exception.base.MyError;
import hovanvydut.apiblog.common.exception.base.MyRuntimeException;
import hovanvydut.apiblog.common.exception.TagNotFoundException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import hovanvydut.apiblog.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@Validated
@Service
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<TagDTO> getTags(int page, int size, String[] sort, String searchKeyword) {

        Sort sortObj = SortAndPaginationUtil.processSort(sort);
        Pageable pageable = PageRequest.of(page - 1, size, sortObj);

        Page<Tag> pageTag;

        if (searchKeyword == null || searchKeyword.isBlank()) {
            pageTag = this.tagRepository.findAll(pageable);
        } else {
            pageTag = this.tagRepository.search(searchKeyword, pageable);
        }

        // mapping Tag list --> TagDTO list
        List<Tag> tags = pageTag.getContent();
        List<TagDTO> tagDTOs = this.modelMapper.map(tags, new TypeToken<List<TagDTO>>() {}.getType());

        return new PageImpl<>(tagDTOs, pageable, pageTag.getTotalElements());
    }

    @Override
    public TagDTO getTag(long tagId) {
        Tag tag = this.tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));

        return this.modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public TagDTO getTag(String slug) {
        Tag tag = this.tagRepository.findBySlug(slug).orElseThrow(() -> new TagNotFoundException(slug));

        return this.modelMapper.map(tag, TagDTO.class);
    }

    @Override
    @Transactional
    public TagDTO createTag(@Valid CreateTagDTO dto) {
        List<MyError> errors = checkUnique(dto);
        if (errors.size() > 0) {
            throw new MyRuntimeException(errors);
        }

        Tag tag = this.modelMapper.map(dto, Tag.class);

        if (tag.getSlug() == null) {
            tag.setSlug(SlugUtil.slugify(tag.getName()));
        }

        Tag savedTag = this.tagRepository.save(tag);
        return this.modelMapper.map(savedTag, TagDTO.class);
    }

    @Override
    @Transactional
    public TagDTO updateTag(long tagId, UpdateTagDTO dto) {
        List<MyError> errors = checkUnique(tagId, dto);
        if (errors.size() > 0) {
            throw new MyRuntimeException(errors);
        }

        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));

        this.modelMapper.map(dto, tag);

        if (tag.getSlug() == null) {
            tag.setSlug(SlugUtil.slugify(tag.getName()));
        }

        Tag savedTag = this.tagRepository.save(tag);

        return this.modelMapper.map(savedTag, TagDTO.class);
    }

    @Override
    @Transactional
    public void deleteTag(long tagId) {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
        this.tagRepository.delete(tag);
    }

    private List<MyError> checkUnique(CreateTagDTO dto) {
        List<MyError> errorList = new ArrayList<>();

        this.tagRepository.findByName(dto.getName())
                .ifPresent(tag -> errorList.add(new MyError().setSource("name").setMessage("The name has already been taken")));

        this.tagRepository.findBySlug(dto.getSlug())
                .ifPresent(tag -> errorList.add(new MyError().setSource("slug").setMessage("The slug has already been taken")));

        return errorList;
    }

    private List<MyError> checkUnique(long id, UpdateTagDTO dto) {
        List<MyError> errorList = new ArrayList<>();

        this.tagRepository.findByName(dto.getName()).ifPresent(tag -> {
            if (tag.getId() != id) {
                errorList.add(new MyError().setSource("name").setMessage("The name has already been taken"));
            }
        });

        this.tagRepository.findBySlug(dto.getSlug()).ifPresent(tag -> {
            if (tag.getId() != id) {
                errorList.add(new MyError().setSource("slug").setMessage("The slug has already been taken"));
            }
        });

        return errorList;
    }
}
