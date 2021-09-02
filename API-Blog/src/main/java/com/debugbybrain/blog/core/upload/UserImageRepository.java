package com.debugbybrain.blog.core.upload;

import com.debugbybrain.blog.entity.UserImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/13/21
 */

@Repository
public interface UserImageRepository extends PagingAndSortingRepository<UserImage, Long> {

    @Query("SELECT i FROM UserImage i WHERE i.user.id = ?1")
    Page<UserImage> findAllByUserId(Long userId, Pageable pageable);

}
