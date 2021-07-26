package hovanvydut.apiblog.core.user;

import hovanvydut.apiblog.model.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
    
}
