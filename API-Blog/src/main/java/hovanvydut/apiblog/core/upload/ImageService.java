package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import org.springframework.data.domain.Page;

/**
 * @author hovanvydut
 * Created on 8/3/21
 */

public interface ImageService {
    Page<UserImageDTO> getImageOfUser(String username, int page, int size);
}
