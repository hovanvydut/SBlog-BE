package hovanvydut.apiblog.core.tag.service;

import hovanvydut.apiblog.common.exception.TagExistingException;
import hovanvydut.apiblog.common.exception.TagNotFoundException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import hovanvydut.apiblog.core.tag.repository.TagRepository;
import hovanvydut.apiblog.model.entity.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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

        if (page <= 0) {
            page = 1;
        }

        if (size <= 0) {
            // NOTE: use default constant
            size = 5;
        }

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
        List<TagDTO> tagDTOs = this.modelMapper.map(tags, new TypeToken<List<Tag>>() {}.getType());

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
        Tag tag = this.tagRepository.findBySlug(slug);

        if (tag == null) {
            throw new TagNotFoundException(slug);
        }

        return this.modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public TagDTO createTag(@Valid CreateTagDTO dto) {
        Tag existTag = this.tagRepository.findByName(dto.getName());
        if (existTag != null) {
            throw new TagExistingException(dto.getName());
        }

        Tag tag = this.modelMapper.map(dto, Tag.class);
        tag.setSlug(SlugUtil.slugify(tag.getName()));

        Tag savedTag = this.tagRepository.save(tag);
        return this.modelMapper.map(savedTag, TagDTO.class);
    }

    @Override
    public TagDTO updateTag(long tagId, UpdateTagDTO dto) {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));

        this.modelMapper.map(dto, tag);
        tag.setSlug(SlugUtil.slugify(tag.getName()));

        Tag savedTag = this.tagRepository.save(tag);

        return this.modelMapper.map(savedTag, TagDTO.class);
    }

    @Override
    public void deleteTag(long tagId) {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
        this.tagRepository.delete(tag);
    }
}
