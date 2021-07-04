package hovanvydut.apiblog.core.auth;

import hovanvydut.apiblog.model.entity.UserRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hovanvydut
 * Created on 7/4/21
 */

@Repository
public interface UserRegistrationRepository extends CrudRepository<UserRegistration, Long> {

    UserRegistration findByEmail(String email);

    UserRegistration findByUsername(String username);

    UserRegistration findByEmailOrUsername(String email, String username);

    UserRegistration findByRegistrationToken(String token);
}
