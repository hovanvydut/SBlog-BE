package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.entity.Follower;
import hovanvydut.apiblog.entity.FollowerId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Repository
public interface FollowerRepository extends PagingAndSortingRepository<Follower, FollowerId> {

}
