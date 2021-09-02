package com.debugbybrain.blog.core.upload;

import com.debugbybrain.blog.common.ExpectedSizeImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 7/6/21
 */

public interface UploadService {
    String save(MultipartFile multipartFile, String uploadDir, boolean isGenThumbnail, ExpectedSizeImage size, ExpectedSizeImage thumbnailExpectSize) throws IOException;
    void deleteImageByDirAndFileName(String dirAndFileName, boolean isDeleteThumbnail) throws IOException;
}
