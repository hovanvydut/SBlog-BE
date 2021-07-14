package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 7/6/21
 */

public interface UploadService {

    public UserImageDTO save(MultipartFile multipartFile, String uploadDir, String ownerUsername) throws IOException;

    public void deleteImageById(long imageId, String ownerUsername) throws IOException;
}
