package hovanvydut.apiblog.api.v1.image;

import hovanvydut.apiblog.core.upload.UploadService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    private final UploadService uploadService;
    private final ModelMapper modelMapper;

    public ImageController(UploadService uploadService, ModelMapper modelMapper) {
        this.uploadService = uploadService;
        this.modelMapper = modelMapper;
    }


}
