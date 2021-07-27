package hovanvydut.apiblog.api.v1.tag;

import hovanvydut.apiblog.api.v1.tag.dto.*;
import hovanvydut.apiblog.core.tag.TagService;
import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@RestController
@RequestMapping("/api/v1/tags")
@Api(tags = "Hash Tag", value = "HashTag")
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;
    private final RedisTemplate redisTemplate;

    public TagController(TagService tagService, ModelMapper modelMapper, RedisTemplate redisTemplate) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation(value = "Get all tags", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("")
    public ResponseEntity<TagPageResp> getAllTags(@Valid TagPaginationParams req) {

        Page<TagDTO> pageTags = this.tagService.getTags(req.getPage(),
                req.getSize(), req.getSort(), req.getKeyword());

        return ResponseEntity.ok(this.modelMapper.map(pageTags, TagPageResp.class));
    }

    @ApiOperation(value = "Get Tag by id")
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

    @ApiOperation(value = "Create a new tag")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<TagResp> createTag(@Valid @RequestBody CreateTagReq req) {
        CreateTagDTO dto = this.modelMapper.map(req, CreateTagDTO.class);
        TagDTO tagDTO = this.tagService.createTag(dto);

        return ResponseEntity.ok(this.modelMapper.map(tagDTO, TagResp.class));
    }

    @ApiOperation(value = "Update tag by id")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<TagResp> updateTag(@PathVariable("id") Long tagId, @Valid @RequestBody UpdateTagReq req) {
        UpdateTagDTO dto = this.modelMapper.map(req, UpdateTagDTO.class);
        TagDTO tagDTO = this.tagService.updateTag(tagId, dto);

        return ResponseEntity.ok(this.modelMapper.map(tagDTO, TagResp.class));
    }

    @ApiOperation(value = "Delete tag by id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable("id") Long tagId) {
        this.tagService.deleteTag(tagId);
    }

}
