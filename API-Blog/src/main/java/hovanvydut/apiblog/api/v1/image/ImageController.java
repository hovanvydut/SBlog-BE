package hovanvydut.apiblog.api.v1.image;

import hovanvydut.apiblog.api.v1.image.dto.ImagePageResp;
import hovanvydut.apiblog.api.v1.image.dto.ImagePaginationParams;
import hovanvydut.apiblog.api.v1.image.dto.UserImageResp;
import hovanvydut.apiblog.core.upload.ImageService;
import hovanvydut.apiblog.core.upload.UploadService;
import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import hovanvydut.apiblog.core.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    private final UploadService uploadService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ImageService imageService;

    public ImageController(UploadService uploadService, ModelMapper modelMapper, UserService userService,
                           ImageService imageService) {
        this.uploadService = uploadService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.imageService = imageService;
    }

    @ApiOperation(value = "Get all your images")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/gallery/images")
    public ResponseEntity<ImagePageResp> getImagesOfUser(@Valid ImagePaginationParams params, Principal principal) {
        Page<UserImageDTO> imageDtoPage = this.imageService.getImageOfUser(principal.getName(), params.getPage(),
                params.getSize());

        return ResponseEntity.ok(this.modelMapper.map(imageDtoPage, ImagePageResp.class));
    }

    @ApiOperation(value = "Upload image")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/gallery/images")
    public ResponseEntity<Object> uploadImageGallery(@RequestParam("image") MultipartFile multipartFile,
                                                     Principal principal) throws IOException {
        String uploadDir = "users/" + principal.getName();

        UserImageDTO userImageDTO = this.userService.uploadImageGallery(multipartFile, uploadDir,principal.getName());

        return ResponseEntity.ok(this.modelMapper.map(userImageDTO, UserImageResp.class));
    }

    @ApiOperation(value = "Delete image")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/me/gallery/images/{imageId}")
    public void deleteImageGallery(@PathVariable long imageId, Principal principal) throws IOException {
        this.userService.deleteImageGallery(imageId, principal.getName());
    }

}
