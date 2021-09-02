package com.debugbybrain.blog.core.user;

import com.debugbybrain.blog.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM VerificationToken t WHERE t.expireAt <= ?1")
    void deleteAllExpiredSince(LocalDateTime now);
    
}
