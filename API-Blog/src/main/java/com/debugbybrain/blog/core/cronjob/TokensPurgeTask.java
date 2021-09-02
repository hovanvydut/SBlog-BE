package com.debugbybrain.blog.core.cronjob;

import com.debugbybrain.blog.core.user.PasswordResetTokenRepository;
import com.debugbybrain.blog.core.user.VerificationTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@Service
@Transactional
public class TokensPurgeTask {

    private final VerificationTokenRepository verifyTokenRepo;
    private final PasswordResetTokenRepository pwdResetTokenRepo;

    public TokensPurgeTask(VerificationTokenRepository verifyTokenRepo, PasswordResetTokenRepository pwdResetTokenRepo) {
        this.verifyTokenRepo = verifyTokenRepo;
        this.pwdResetTokenRepo = pwdResetTokenRepo;
    }

    @Scheduled(cron = "${purge.cron.verification-token.expression}")
    public void purgeExpired() {
        this.verifyTokenRepo.deleteAllExpiredSince(LocalDateTime.now());
    }

    @Scheduled(cron = "${purge.cron.reset-pwd-token.expression}")
    public void purgeExpiredPasswordResetToken() {
        this.pwdResetTokenRepo.deleteAllExpiredSince(LocalDateTime.now());
    }
}
