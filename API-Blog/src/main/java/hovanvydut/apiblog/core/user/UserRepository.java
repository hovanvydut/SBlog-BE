package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> getUserAndRoleByUsername(@Param("username") String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %?1%")
    Page<User> search(String keyword, Pageable pageable);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Optional<Long> getUserIdByUsername(@Param("username") String username);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Long> getUserIdByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.avatar = ?2 WHERE u.id = ?1")
    void updateAvatar(Long userId, String uploadDir);
}
