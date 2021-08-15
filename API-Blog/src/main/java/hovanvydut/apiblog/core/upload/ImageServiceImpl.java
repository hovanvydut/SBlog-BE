package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.common.util.SortAndPaginationUtil;
import hovanvydut.apiblog.core.upload.dto.UserImageDTO;
import hovanvydut.apiblog.core.user.UserService;
import hovanvydut.apiblog.entity.UserImage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hovanvydut
 * Created on 8/3/21
 */

@Service
public class ImageServiceImpl implements ImageService{

    private final UserImageRepository imageRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ImageServiceImpl(UserImageRepository imageRepo, UserService userService, ModelMapper modelMapper) {
        this.imageRepo = imageRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<UserImageDTO> getImageOfUser(String username, int page, int size) {
        Long userId = this.userService.getUserIdByUsername(username);
        Pageable pageable = SortAndPaginationUtil.processSortAndPagination(page, size, null);

        Page<UserImage> userImagePage = this.imageRepo.findAllByUserId(userId, pageable);
        List<UserImage> userImages = userImagePage.getContent();
        List<UserImageDTO> userImageDtos = this.modelMapper.map(userImages, new TypeToken<List<UserImage>> () {}.getType());

        return new PageImpl<>(userImageDtos, pageable, userImagePage.getTotalElements());
    }
}
