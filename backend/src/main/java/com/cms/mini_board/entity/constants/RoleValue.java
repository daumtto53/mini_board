package com.cms.mini_board.entity.constants;

public enum RoleValue {
    USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

    public final String role;

    RoleValue(String role) {
        this.role = role;
    }
}
