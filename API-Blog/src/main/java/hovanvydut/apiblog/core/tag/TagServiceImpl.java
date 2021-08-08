package hovanvydut.apiblog.core.tag;

import hovanvydut.apiblog.common.ExpectedSizeImage;
import hovanvydut.apiblog.common.exception.TagNotFoundException;
import hovanvydut.apiblog.common.exception.base.MyError;
import hovanvydut.apiblog.common.exception.base.MyRuntimeException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import hovanvydut.apiblog.core.upload.UploadService;
import hovanvydut.apiblog.entity.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@Validated
@Service
public class TagServiceImpl implements TagService{

    private final UploadService uploadService;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Value("${endpointImageUrl}")
    private String hostUploadUrl;

    public TagServiceImpl(UploadService uploadService, TagRepository tagRepository, ModelMapper modelMapper) {
        this.uploadService = uploadService;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<TagDTO> getTags(int page, int size, String[] sort, String searchKeyword) {
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, sort);

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
        System.out.println(tag);

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

    @Override
    @Transactional
    public TagDTO uploadImage(long tagId, MultipartFile multipartFile) throws IOException {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));

        if ((tag.getImage() != null) && (!tag.getImage().isBlank())) {
            this.uploadService.deleteImageByDirAndFileName(tag.getImage(), true);
        }

        ExpectedSizeImage sizeImage = new ExpectedSizeImage(200, 200);
        String dirAndFileName = this.uploadService.save(multipartFile, "tags/" + tagId, false, sizeImage, null);
        tag.setImage(dirAndFileName);

        Tag savedTag = this.tagRepository.save(tag);
        savedTag.setImage(hostUploadUrl + "/" + savedTag.getImage());

        return this.modelMapper.map(savedTag, TagDTO.class);
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
