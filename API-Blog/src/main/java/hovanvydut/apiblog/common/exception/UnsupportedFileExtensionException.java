package hovanvydut.apiblog.common.exception;

import hovanvydut.apiblog.common.exception.base.UnsupportedException;

/**
 * @author hovanvydut
 * Created on 7/9/21
 */

public class UnsupportedFileExtensionException extends UnsupportedException {

    public UnsupportedFileExtensionException(String fileExtension) {
        super(fileExtension + " format is not supported");
    }

    public UnsupportedFileExtensionException(String message, Throwable ex) {
        super(message, ex);
    }
}
