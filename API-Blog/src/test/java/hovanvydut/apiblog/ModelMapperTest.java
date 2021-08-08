package hovanvydut.apiblog;

import hovanvydut.apiblog.core.tag.dto.UpdateTagDTO;
import hovanvydut.apiblog.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author hovanvydut
 * Created on 7/1/21
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModelMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeAll
    public void initAll() {
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    @Test
    @DisplayName("Mapping UpdateTagDTO --> existing Tag entity")
    public void testMapping1() {
        Tag tag = new Tag().setId(1L).setName("Javascript").setSlug("javascript")
                .setImage("link image 1")
                .setCreatedAt(LocalDateTime.now());

        UpdateTagDTO updateTagDTO = new UpdateTagDTO().setName("Docker").setImage("link image 2").setLastEditedAt(LocalDateTime.now());

        this.modelMapper.map(updateTagDTO, tag);

        assertThat(tag.getLastEditedAt()).isEqualTo(updateTagDTO.getLastEditedAt());
        assertThat(tag.getName()).isEqualTo(updateTagDTO.getName());
        assertThat(tag.getImage()).isEqualTo(updateTagDTO.getImage());
    }
}
