package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.request.CreateTagReq;
import hovanvydut.apiblog.api.v1.request.UpdateTagReq;
import hovanvydut.apiblog.api.v1.response.TagPageResp;
import hovanvydut.apiblog.api.v1.response.TagResp;
import hovanvydut.apiblog.common.constant.PagingConstant;
import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import hovanvydut.apiblog.core.tag.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;
    private final RedisTemplate redisTemplate;

    public TagController(TagService tagService, ModelMapper modelMapper, RedisTemplate redisTemplate) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("")
    public ResponseEntity<TagPageResp> getAllTags(@RequestParam(required = false) Optional<String> keyword,
                                                  @RequestParam(required = false) Optional<Integer> page,
                                                  @RequestParam(required = false) Optional<Integer> size,
                                                  @RequestParam(required = false, defaultValue = "id,asc") String[] sort) {

        Page<TagDTO> pageTags = this.tagService.getTags(page.orElse(1),
                size.orElse(PagingConstant.TAGS_PER_PAGE), sort, keyword.orElse(""));

        return ResponseEntity.ok(this.modelMapper.map(pageTags, TagPageResp.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResp> getTag(@PathVariable("id") Long tagId) {

        if (this.redisTemplate.opsForValue().get("lastUser" + tagId) != null) {
            TagDTO oldTag = (TagDTO) this.redisTemplate.opsForValue().get("lastUser");
            return ResponseEntity.ok(this.modelMapper.map(oldTag, TagResp.class));
        }

        TagDTO tagDTO = this.tagService.getTag(tagId);

        this.redisTemplate.opsForValue().set("lastUser" + tagId, tagDTO, 60, TimeUnit.SECONDS);

        return ResponseEntity.ok(this.modelMapper.map(tagDTO, TagResp.class));
    }

    @PostMapping("")
    public TagResp createTag(@Valid @RequestBody CreateTagReq req) {
        CreateTagDTO dto = this.modelMapper.map(req, CreateTagDTO.class);
        TagDTO tagDTO = this.tagService.createTag(dto);

        return this.modelMapper.map(tagDTO, TagResp.class);
    }

    @PutMapping("/{id}")
    public TagResp updateTag(@PathVariable("id") Long tagId, @Valid @RequestBody UpdateTagReq req) {
        UpdateTagDTO dto = this.modelMapper.map(req, UpdateTagDTO.class);
        TagDTO tagDTO = this.tagService.updateTag(tagId, dto);

        return this.modelMapper.map(tagDTO, TagResp.class);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long tagId) {
        this.tagService.deleteTag(tagId);
    }

}
