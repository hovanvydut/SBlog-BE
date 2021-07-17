package hovanvydut.apiblog.core.tag;

import hovanvydut.apiblog.core.tag.dto.CreateTagDTO;
import hovanvydut.apiblog.core.tag.dto.TagDTO;
import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import org.springframework.data.domain.Page;

import javax.validation.Valid;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

public interface TagService {
    public Page<TagDTO> getTags(int page, int size, String[] sort, String searchKeyword);
    public TagDTO getTag(long tagId);
    public TagDTO getTag(String slug);
    public TagDTO createTag(@Valid CreateTagDTO dto);
    public TagDTO updateTag(long tagId, UpdateTagDTO dto);
    public void deleteTag(long tagId);
}
