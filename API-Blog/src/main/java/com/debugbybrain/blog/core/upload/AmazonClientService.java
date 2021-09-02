package com.debugbybrain.blog.core.upload;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.debugbybrain.blog.common.ExpectedSizeImage;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author hovanvydut
 * Created on 7/14/21
 */

@Service
public class AmazonClientService {
    private AmazonS3 s3;

    @Value("${endpointImageUrl}")
    private String endpointUrl;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile(MultipartFile multipartFile, String dirAndFileName,
                             boolean isGenThumbnail, ExpectedSizeImage expectedSize, ExpectedSizeImage thumbnailExpectSize)
            throws IOException {
        File file = convertMultiPartToFile(multipartFile);

        setSizeImage(file, file, expectedSize);

        uploadFileTos3bucket(dirAndFileName, file);

        if (isGenThumbnail) {
            File thumbnailFile = new File(multipartFile.getOriginalFilename());

            setSizeImage(file, file, thumbnailExpectSize);

            Thumbnails.of(ImageIO.read(file))
                    .width(200)
                    .toFile(thumbnailFile);
            uploadFileTos3bucket("thumbnails/" + dirAndFileName, thumbnailFile);

            thumbnailFile.delete();
        }

        file.delete();

        return dirAndFileName;
    }

    public String deleteFileFromS3Bucket(String dirAndFileName) {
        System.out.println(dirAndFileName);
        this.s3.deleteObject(new DeleteObjectRequest(bucketName, dirAndFileName));
        return "Successfully deleted";
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        this.s3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private void setSizeImage(File inputFile, File outputFile, ExpectedSizeImage expectedSize) throws IOException {
        if (!Objects.isNull(expectedSize)) {
            Thumbnails.Builder thumbBuilder = Thumbnails.of(ImageIO.read(inputFile));
            if (!Objects.isNull(expectedSize.getWidth())) {
                thumbBuilder.width(expectedSize.getWidth());
            }

            if (!Objects.isNull(expectedSize.getHeight())) {
                thumbBuilder.height(expectedSize.getHeight());
            }

            thumbBuilder.toFile(outputFile);
        }
    }

}
