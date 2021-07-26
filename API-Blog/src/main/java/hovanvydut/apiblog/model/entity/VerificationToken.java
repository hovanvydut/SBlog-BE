package hovanvydut.apiblog.model.entity;

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
@Table(name = "verification_token")
public class VerificationToken {

    // calculate by minutes
    private static final int EXPIRATION = 1 * (24 * 60);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true, length = 255)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;

    public void setCreatedAt(LocalDateTime time) {
        this.createdAt = time;
        this.expireAt = this.createdAt.plusMinutes(EXPIRATION);
    }

    @PrePersist
    protected void onPersist() {
        this.expireAt = this.createdAt.plusMinutes(EXPIRATION);
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(this.expireAt);
    }
}
