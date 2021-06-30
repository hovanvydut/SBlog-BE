package hovanvydut.apiblog.core.tag.service;

import hovanvydut.apiblog.model.entity.Tag;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

public interface TagService {
    public Tag getTag(long tagId);
    public Tag getTag(String slug);
    public Tag createTag(Tag tag);
    public Tag updateTag(long tagId, Tag tagRequest);
    public void deleteTag(long tagId);
}
