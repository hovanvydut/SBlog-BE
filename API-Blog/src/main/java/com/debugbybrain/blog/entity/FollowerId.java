package com.debugbybrain.blog.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author hovanvydut
 * Created on 7/12/21
 */

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@ToString
@Embeddable
public class FollowerId implements Serializable {
    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "to_user_id")
    private Long toUserId;
}