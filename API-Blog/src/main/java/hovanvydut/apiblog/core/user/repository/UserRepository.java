package hovanvydut.apiblog.core.user.repository;

import hovanvydut.apiblog.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsernameIs(String username);

    User findByEmailIs(String email);

}
