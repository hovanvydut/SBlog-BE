package com.debugbybrain.blog.core.upload;

import com.debugbybrain.blog.core.upload.dto.UserImageDTO;
import org.springframework.data.domain.Page;

/**
 * @author hovanvydut
 * Created on 8/3/21
 */

public interface ImageService {
    Page<UserImageDTO> getImageOfUser(String username, int page, int size);
}
