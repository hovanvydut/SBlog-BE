package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.model.entity.UserRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Repository
public interface UserRegistrationRepository extends CrudRepository<UserRegistration, Long> {

    Optional<UserRegistration> findByEmail(String email);

    Optional<UserRegistration> findByUsername(String username);

    Optional<UserRegistration> findByEmailOrUsername(String email, String username);

    Optional<UserRegistration> findByRegistrationToken(String token);
}
