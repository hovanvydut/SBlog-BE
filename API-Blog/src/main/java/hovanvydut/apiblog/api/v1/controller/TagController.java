package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.CreateTagReq;
import hovanvydut.apiblog.api.v1.request.UpdateTagReq;
import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import hovanvydut.apiblog.core.tag.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;

    public TagController(TagService tagService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public Page<TagDTO> getAllTags(@RequestParam(required = false) Optional<String> keyword,
                                   @RequestParam(required = false) Optional<Integer> page,
                                   @RequestParam(required = false) Optional<Integer> size,
                                   @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

        // NOTE: USERS_PER_PAGE
        final int USERS_PER_PAGE = 3;
        Page<TagDTO> pageTags = this.tagService.getTags(page.orElse(1), size.orElse(USERS_PER_PAGE), sort, keyword.orElse(""));
        return pageTags;
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable("id") Long tagId) {
        return this.tagService.getTag(tagId);
    }

    @PostMapping("")
    public TagDTO createTag(@Valid @RequestBody CreateTagReq req) {
        CreateTagDTO dto = this.modelMapper.map(req, CreateTagDTO.class);
        return this.tagService.createTag(dto);
    }

    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable("id") Long tagId, @Valid @RequestBody UpdateTagReq req) {
        UpdateTagDTO dto = this.modelMapper.map(req, UpdateTagDTO.class);
        return this.tagService.updateTag(tagId, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long tagId) {
        this.tagService.deleteTag(tagId);
    }

}
