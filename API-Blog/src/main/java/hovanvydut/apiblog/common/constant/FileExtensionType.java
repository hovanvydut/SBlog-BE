package hovanvydut.apiblog.common.constant;

/**
 * @author hovanvydut
 * Created on 7/7/21
 */

public enum FileExtensionType {
    JPEG("jpeg"), JPG("jpg"), PNG("png"), GIF("gif");

    private String typeName;

    FileExtensionType(String typeName) {
        this.typeName = typeName;
    }

    public String get() {
        return this.typeName;
    }
}
