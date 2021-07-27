package hovanvydut.apiblog.model.entity;

import hovanvydut.apiblog.common.constant.ExpirationTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION = ExpirationTime.PASSWORD_RESET_TOKEN;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", unique = true, nullable = false, length = 255)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;

    @PrePersist
    protected void onPersist() {
        this.expireAt = this.createdAt.plusMinutes(EXPIRATION);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireAt);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.expireAt = this.createdAt.plusMinutes(EXPIRATION);
    }

}
