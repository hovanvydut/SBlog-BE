package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.common.util.FileUploadUtil;
import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.core.user.dto.UserDTO;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.UserImage;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

@Service
public class LocalUploadService implements UploadService {

    private final UserImageRepository userImageRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Value("${endpointImageUrl}")
    private String endpointUrl;

    public LocalUploadService(UserImageRepository userImageRepo,
                              UserService userService,
                              ModelMapper modelMapper) {
        this.userImageRepo = userImageRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public UserImageDTO save(MultipartFile multipartFile, String uploadDir, String ownerUsername) throws IOException {
        UserDTO userDTO = this.userService.getUserByUsername(ownerUsername);

        FileUploadUtil.verifyFileSize(multipartFile);
        FileUploadUtil.verifyFileExtension(multipartFile);
        FileUploadUtil.verifyMIMEtype(multipartFile);
        FileUploadUtil.verifySignatureFile(multipartFile);

        String originFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extensionFile = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        String fileName = FileUploadUtil.generateFileName(multipartFile);

        Path imageUploadPath = Paths.get("src/main/resources/static/uploaded/images/" + uploadDir);

        if (!Files.exists(imageUploadPath)) {
            Files.createDirectories(imageUploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {

            // copy all input stream --> byte array to use multiple
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, baos);
            byte[] bytes = baos.toByteArray();

            Path imagePath = imageUploadPath.resolve(fileName);

            // save file on disk
            Files.copy(new ByteArrayInputStream(bytes), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // generate thumbnail and save
            Path thumbnailPath = Paths.get( "src/main/resources/static/uploaded/images/thumbnails/" + uploadDir);
            String thumbnailPathString = thumbnailPath + File.separator + fileName;

            if (!Files.exists(thumbnailPath)) {
                Files.createDirectories(thumbnailPath);
            }

            Thumbnails.of(ImageIO.read(new BufferedInputStream(new ByteArrayInputStream(bytes))))
                    .width(200)
                    .toFile(new File(thumbnailPathString));

            String slug = uploadDir + "/" + fileName;

            UserImage userImage = new UserImage()
                    .setSlug(slug)
                    .setUser(new User().setId(userDTO.getId()));
            UserImage savedUserImage = this.userImageRepo.save(userImage);

            UserImageDTO userImageDTO = this.modelMapper.map(savedUserImage, UserImageDTO.class);
            userImageDTO.setSlug(this.endpointUrl + "/" + userImageDTO.getSlug());
            return userImageDTO;
        } catch (IOException ex) {
            throw new IOException("Could not save image file: " + fileName, ex);
        }
    }

    @Override
    public void deleteImageById(long imageId, String ownerUsername) throws IOException {
        UserDTO userDTO = this.userService.getUserByUsername(ownerUsername);

        UserImage userImage = this.userImageRepo.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (userDTO.getId() != userImage.getUser().getId()) {
            throw new RuntimeException("Not owning this image");
        }

        Path imagePath = Paths.get("src/main/resources/static/uploaded/images/" + userImage.getSlug());
        Path thumbnailPath = Paths.get("src/main/resources/static/uploaded/images/thumbnails/" + userImage.getSlug());

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
        }

        if (Files.exists(thumbnailPath)) {
            Files.delete(thumbnailPath);
        }
    }
}
