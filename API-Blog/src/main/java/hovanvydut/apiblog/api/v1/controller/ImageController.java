package hovanvydut.apiblog.api.v1.controller;

import hovanvydut.apiblog.api.v1.response.UserImageResp;
import hovanvydut.apiblog.core.upload.UploadService;
import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    public ImageController(UploadService uploadService, ModelMapper modelMapper) {
        this.uploadService = uploadService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/me/images")
    public void getImagesOfUser(Principal principal) {
        // must have pagination
        // get all images of user
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/images")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile multipartFile,
                                              Principal principal) throws IOException {

        String uploadDir = "users/" + principal.getName();

        UserImageDTO userImageDTO = this.uploadService
                .save(multipartFile, uploadDir, principal.getName());

        return ResponseEntity.ok(this.modelMapper.map(userImageDTO, UserImageResp.class));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/me/images/{imageId}")
    public void deleteImage(@PathVariable long imageId, Principal principal) throws IOException {
        this.uploadService.deleteImageById(imageId, principal.getName());
    }
}
