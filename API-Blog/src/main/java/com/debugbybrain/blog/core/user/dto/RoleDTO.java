package com.debugbybrain.blog.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author hovanvydut
 * Created on 9/6/21
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return id == roleDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
