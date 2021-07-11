package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.model.entity.Tag;
import hovanvydut.apiblog.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %?1%")
    Page<User> search(String keyword, Pageable pageable);

}
