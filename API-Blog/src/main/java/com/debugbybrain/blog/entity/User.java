package com.debugbybrain.blog.entity;

import com.debugbybrain.blog.entity.enums.GenderEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hovanvydut
 * Created on 7/3/21
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false, length = 32)
    private String fullName;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "username", unique = true, nullable = false, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "is_using_2FA", nullable = false)
    private boolean isUsing2FA = false;

    @Column(name = "secret", nullable = true, length = 255)
    private String secret;

    @Column(name = "avatar", nullable = false, length = 255)
    private String avatar = "default avatar url nek";

    @Column(name = "host_avatar", nullable = false, length = 255)
    private String hostAvatar;

    @Column(name = "birthday", nullable = true)
    private LocalDate birthday;

    @Column(name = "gender", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender = GenderEnum.UNKNOWN;

    @Column(name = "biography", nullable = true, length = 255)
    private String biography;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "toUser")
    private Set<Follower> followers;

    @OneToMany(mappedBy = "fromUser")
    private Set<Follower> followings;

    @OneToMany(mappedBy = "author")
    private Set<Article> articles;

    @OneToMany(mappedBy = "user")
    private List<UserImage> images = new ArrayList<>();

}
