package com.debugbybrain.blog.core.upload;

import com.debugbybrain.blog.common.ExpectedSizeImage;
import com.debugbybrain.blog.common.util.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

@Service
@Primary
public class AwsUploadService implements UploadService {

    private final UserImageRepository userImageRepo;
    private final ModelMapper modelMapper;
    private final AmazonClientService amazonClientService;

    @Value("${endpointImageUrl}")
    private String endpointUrl;

    public AwsUploadService(UserImageRepository userImageRepo,
                            ModelMapper modelMapper,
                            AmazonClientService amazonClientService) {
        this.userImageRepo = userImageRepo;
        this.modelMapper = modelMapper;
        this.amazonClientService = amazonClientService;
    }

    @Override
    public String save(MultipartFile multipartFile, String uploadDir, boolean isGenThumbnail,
                       ExpectedSizeImage size, ExpectedSizeImage thumbnailExpectSize)
            throws IOException {
        FileUploadUtil.verifyFileSize(multipartFile);
        FileUploadUtil.verifyFileExtension(multipartFile);
        FileUploadUtil.verifyMIMEtype(multipartFile);
        FileUploadUtil.verifySignatureFile(multipartFile);

        // save file on aws s3
        uploadDir = "images/" + uploadDir;
        String dirAndFileName = uploadDir + "/" + FileUploadUtil.generateFileName(multipartFile);

        this.amazonClientService.uploadFile(multipartFile, dirAndFileName, isGenThumbnail, size, thumbnailExpectSize);

        return dirAndFileName;
    }

    @Override
    public void deleteImageByDirAndFileName(String dirAndFileName, boolean isDeleteThumbnail) {
        this.amazonClientService.deleteFileFromS3Bucket(dirAndFileName);

        if (isDeleteThumbnail) {
            this.amazonClientService.deleteFileFromS3Bucket("thumbnails/" + dirAndFileName);
        }
    }

}
