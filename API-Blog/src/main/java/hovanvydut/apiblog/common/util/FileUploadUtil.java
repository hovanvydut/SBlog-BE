package hovanvydut.apiblog.common.util;

import hovanvydut.apiblog.common.constant.FileExtensionType;
import hovanvydut.apiblog.common.constant.FileMimeType;
import hovanvydut.apiblog.common.constant.FileSignatureType;
import hovanvydut.apiblog.common.exception.UnsupportedFileExtensionException;
import hovanvydut.apiblog.common.exception.UnsupportedFileMimeTypeException;
import hovanvydut.apiblog.common.exception.UnsupportedFileSignatureException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

public class FileUploadUtil {
    public static void verifyFileExtension(MultipartFile multipartFile) {
        String originFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extensionFile = originFileName.substring(originFileName.lastIndexOf(".") + 1).toLowerCase();

        List<String> validExtension = new ArrayList<>(List.of(
                FileExtensionType.JPEG.get(),
                FileExtensionType.JPG.get(),
                FileExtensionType.PNG.get(),
                FileExtensionType.GIF.get()
        ));

        if (!validExtension.contains(extensionFile)) {
            throw new UnsupportedFileExtensionException(extensionFile);
        }
    }

    public static void verifyMIMEtype(MultipartFile multipartFile) {
        String fileMimeTypeName = multipartFile.getContentType();

        List<String> validMIMEtype = new ArrayList<>(List.of(
                FileMimeType.JPEG.get(),
                FileMimeType.PNG.get(),
                FileMimeType.GIF.get()
        ));

        if (!validMIMEtype.contains(fileMimeTypeName)) {
            throw new UnsupportedFileMimeTypeException(fileMimeTypeName);
        }
    }

    public static void verifyFileSize(MultipartFile multipartFile) {
        final int MAX_SIZE = 500 * 1024; // 500 Kb

        if (multipartFile.getSize() > MAX_SIZE) {
            throw new MaxUploadSizeExceededException(MAX_SIZE);
        }
    }

    public static String generateFileName(MultipartFile multipartFile) {
        String originFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String filePrefix = originFileName.substring(0, originFileName.lastIndexOf(".")).replace("\\s+", "_");
        String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Random random = new Random();

        return filePrefix + "_" + fileSuffix + "_" + Math.abs(random.nextInt());
    }


    // handle check signatures file
    public static void verifySignatureFile(MultipartFile multipartFile) throws IOException {
        String originFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extensionFile = originFileName.substring(originFileName.lastIndexOf(".") + 1).toLowerCase();

        InputStream in = multipartFile.getInputStream();
        byte[] bytes = new byte[4];
        int read = in.read(bytes);
        if (read == -1) {
            throw new RuntimeException("File has no content");
        }

        System.out.println(bytes);

        String fileType = getFileTypeBySignature(bytes);

        if (fileType == null) {
            throw new UnsupportedFileSignatureException();
        }

        if (!fileType.equals(extensionFile)) {
            throw new RuntimeException("Signature and extension file don't match");
       }
    }

    private static String getFileTypeBySignature(byte[] b) {
        String res = null;
        String fileCode = bytesToHexString(b);

        Iterator<Map.Entry<String, String>> entryIter = FileSignatureType.FILE_SIGNATURE_TYPE.entrySet().iterator();

        while (entryIter.hasNext()) {
            Map.Entry<String, String> entry = entryIter.next();
            if (entry.getValue().toLowerCase().startsWith(fileCode.toLowerCase())
                    || fileCode.toLowerCase().startsWith(entry.getValue().toLowerCase())) {
                res = entry.getKey();
                break;
            }
        }

        return res;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();

        if (src == null || src.length <= 0) {
            return null;
        }

        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }

            stringBuilder.append(hv);
        }

        return stringBuilder.toString();
    }
}
