package hovanvydut.apiblog.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

public class FileSignatureType {
    public static final Map<String, String> FILE_SIGNATURE_TYPE = new HashMap<>();

    private FileSignatureType() {

    }

    static {
        FILE_SIGNATURE_TYPE.put(FileExtensionType.JPEG.get(), "ffd8ff");
        FILE_SIGNATURE_TYPE.put(FileExtensionType.JPG.get(), "ffd8ff");
        FILE_SIGNATURE_TYPE.put(FileExtensionType.PNG.get(), "89504e47");
        FILE_SIGNATURE_TYPE.put(FileExtensionType.GIF.get(), "47494638");
    }
}
