package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %?1%")
    Page<User> search(String keyword, Pageable pageable);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Optional<Long> getUserIdByUsername(@Param("username") String username);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Long> getUserIdByEmail(@Param("email") String email);
}
