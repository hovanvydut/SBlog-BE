package com.debugbybrain.blog.core.user;

import com.debugbybrain.blog.entity.Follower;
import com.debugbybrain.blog.entity.FollowerId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Repository
public interface FollowerRepository extends PagingAndSortingRepository<Follower, FollowerId> {

}
