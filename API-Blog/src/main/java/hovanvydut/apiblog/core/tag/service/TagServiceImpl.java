package hovanvydut.apiblog.core.tag.service;

import hovanvydut.apiblog.common.exception.TagNotFoundException;
import hovanvydut.apiblog.common.util.SlugUtil;
import hovanvydut.apiblog.core.tag.repository.TagRepository;
import hovanvydut.apiblog.model.entity.Tag;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author hovanvydut
 * Created on 6/27/21
 */

@Service
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag getTag(long tagId) {
        return this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
    }

    @Override
    public Tag getTag(String slug) {
        Tag tag = this.tagRepository.findBySlug(slug);

        if (tag == null) {
            throw new TagNotFoundException(slug);
        }

        return tag;
    }

    @Override
    public Tag createTag(Tag tag) {
        return this.tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(long tagId, Tag tagRequest) {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));

        String slug;
        if (tagRequest.getSlug().isBlank()) {
            slug = SlugUtil.slugify(tagRequest.getName());
        } else {
            slug = tagRequest.getSlug();
        }

        LocalDateTime lastEditedAt;
        if (Objects.isNull(tagRequest.getLastEditedAt())) {
            lastEditedAt = LocalDateTime.now();
        } else {
            lastEditedAt = tagRequest.getLastEditedAt();
        }

        tag.setName(tagRequest.getName())
                .setSlug(slug)
                .setImage(tagRequest.getImage())
                .setLastEditedAt(lastEditedAt);

        return this.tagRepository.save(tag);
    }

    @Override
    public void deleteTag(long tagId) {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
        this.tagRepository.delete(tag);
    }
}
