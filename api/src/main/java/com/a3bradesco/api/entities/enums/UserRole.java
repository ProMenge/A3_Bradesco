package com.a3bradesco.api.entities.enums;

public enum UserRole {
    ROLE_USER(1),
    ROLE_ADMIN(2);

    private final int code;

    UserRole(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
