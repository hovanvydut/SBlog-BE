package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.common.util.FileUploadUtil;
import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.UserImage;
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
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AmazonClientService amazonClientService;

    @Value("${endpointImageUrl}")
    private String endpointUrl;

    public AwsUploadService(UserImageRepository userImageRepo,
                            UserService userService,
                            ModelMapper modelMapper,
                            AmazonClientService amazonClientService) {
        this.userImageRepo = userImageRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.amazonClientService = amazonClientService;
    }

    @Override
    public UserImageDTO save(MultipartFile multipartFile, String uploadDir, String ownerUsername) throws IOException {
        UserDTO userDTO = this.userService.getUserByUsername(ownerUsername);

        FileUploadUtil.verifyFileSize(multipartFile);
        FileUploadUtil.verifyFileExtension(multipartFile);
        FileUploadUtil.verifyMIMEtype(multipartFile);
        FileUploadUtil.verifySignatureFile(multipartFile);

        // save file on aws s3
        uploadDir = "images/" + uploadDir;
        String dirAndFileName = uploadDir + "/" + FileUploadUtil.generateFileName(multipartFile);

        String url = this.amazonClientService.uploadFile(multipartFile, dirAndFileName);
        System.out.println(url);

        // persistent on DB
        UserImage userImage = new UserImage()
                .setSlug(dirAndFileName)
                .setUser(new User().setId(userDTO.getId()));
        UserImage savedUserImage = this.userImageRepo.save(userImage);

        UserImageDTO userImageDTO = this.modelMapper.map(savedUserImage, UserImageDTO.class);
        userImageDTO.setSlug(this.endpointUrl + "/" + userImageDTO.getSlug());
        return userImageDTO;
    }

    @Override
    public void deleteImageById(long imageId, String ownerUsername) {
        UserDTO userDTO = this.userService.getUserByUsername(ownerUsername);

        UserImage userImage = this.userImageRepo.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (userDTO.getId() != userImage.getUser().getId()) {
            throw new RuntimeException("Not owning this image");
        }

        this.userImageRepo.deleteById(userImage.getId());
        this.amazonClientService.deleteFileFromS3Bucket(userImage.getSlug());
        this.amazonClientService.deleteFileFromS3Bucket("thumbnails/" + userImage.getSlug());
    }

}
