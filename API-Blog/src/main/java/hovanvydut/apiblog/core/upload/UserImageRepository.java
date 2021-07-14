package hovanvydut.apiblog.core.upload;

import hovanvydut.apiblog.model.entity.UserImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@Repository
public interface UserImageRepository extends PagingAndSortingRepository<UserImage, Long> {

}
