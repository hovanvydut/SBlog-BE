package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.common.util.FileUploadUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

@Service
@Primary
public class LocalUploadService implements UploadService {
    public void save(MultipartFile multipartFile, String uploadDir, String expectedFileName) throws IOException {
        FileUploadUtil.verifyFileSize(multipartFile);
        FileUploadUtil.verifyFileExtension(multipartFile);
        FileUploadUtil.verifyMIMEtype(multipartFile);
        FileUploadUtil.verifySignatureFile(multipartFile);

        // save file on disk
        String originFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extensionFile = originFileName.substring(originFileName.lastIndexOf(".") + 1);
        String fileName = FileUploadUtil.generateFileName(multipartFile) + "." + extensionFile;

        Path uploadPath = Paths.get("src/main/resources/static/" + uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Could not save image file: " + fileName, ex);
        }
    }
}
