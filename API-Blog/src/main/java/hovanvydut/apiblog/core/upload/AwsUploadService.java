package hovanvydut.apiblog.core.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

@Service
public class AwsUploadService implements UploadService {

    @Override
    public void save(MultipartFile multipartFile, String uploadDir, String expectedFileName) throws IOException {

    }

}
