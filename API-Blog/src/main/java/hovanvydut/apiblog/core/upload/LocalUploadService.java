package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.common.ExpectedSizeImage;
import hovanvydut.apiblog.common.util.FileUploadUtil;
import hovanvydut.apiblog.core.user.UserService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    public String save(MultipartFile multipartFile, String uploadDir, boolean isGenThumbnail,
                       ExpectedSizeImage size, ExpectedSizeImage thumbnailExpectSize) throws IOException {

        // FIXME: generate thumbnail via thumbnailExpectSize
        FileUploadUtil.verifyFileSize(multipartFile);
        FileUploadUtil.verifyFileExtension(multipartFile);
        FileUploadUtil.verifyMIMEtype(multipartFile);
        FileUploadUtil.verifySignatureFile(multipartFile);

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
            if (isGenThumbnail) {
                Path thumbnailPath = Paths.get( "src/main/resources/static/uploaded/images/thumbnails/" + uploadDir);
                String thumbnailPathString = thumbnailPath + File.separator + fileName;

                if (!Files.exists(thumbnailPath)) {
                    Files.createDirectories(thumbnailPath);
                }

                Thumbnails.of(ImageIO.read(new BufferedInputStream(new ByteArrayInputStream(bytes))))
                        .width(200)
                        .toFile(new File(thumbnailPathString));
            }

            String slug = uploadDir + "/" + fileName;

            return slug;
        } catch (IOException ex) {
            throw new IOException("Could not save image file: " + fileName, ex);
        }
    }

    @Override
    public void deleteImageByDirAndFileName(String dirAndFileName, boolean isDeleteThumbnail) throws IOException {
        Path imagePath = Paths.get("src/main/resources/static/uploaded/images/" + dirAndFileName);
        Path thumbnailPath = Paths.get("src/main/resources/static/uploaded/images/thumbnails/" + dirAndFileName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
        }

        if (Files.exists(thumbnailPath)) {
            Files.delete(thumbnailPath);
        }
    }
}
