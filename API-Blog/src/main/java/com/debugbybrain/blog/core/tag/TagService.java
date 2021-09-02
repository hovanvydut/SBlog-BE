package com.debugbybrain.blog.core.tag;

import com.debugbybrain.blog.core.tag.dto.CreateTagDTO;
import com.debugbybrain.blog.core.tag.dto.TagDTO;
import com.debugbybrain.blog.core.tag.dto.UpdateTagDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

public interface TagService {
    Page<TagDTO> getTags(int page, int size, String[] sort, String searchKeyword);
    TagDTO getTag(long tagId);
    TagDTO getTag(String slug);
    TagDTO createTag(@Valid CreateTagDTO dto);
    TagDTO updateTag(long tagId, UpdateTagDTO dto);
    void deleteTag(long tagId);
    TagDTO uploadImage(long tagId, MultipartFile multipartFile) throws IOException;
}
