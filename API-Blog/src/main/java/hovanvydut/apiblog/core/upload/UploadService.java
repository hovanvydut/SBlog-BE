package hovanvydut.apiblog.core.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 7/6/21
 */

public interface UploadService {

    public void save(MultipartFile multipartFile, String uploadDir, String expectedFileName) throws IOException;

}
