package com.visma.fishing.model.security;

import com.visma.fishing.model.base.BaseEntity;
import com.visma.fishing.security.utils.RoleName;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ROLES")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleName roleName;

    public Role() {

    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName role) {
        this.roleName = role;
    }


}
