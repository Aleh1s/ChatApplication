package com.palamar.chatapplication.entity.user;

public enum UserPermission {

    SEND_MESSAGE("send:message"),
    READ_MESSAGE("read:message"),
    CREATE_CHAT("create:chat"),
    DELETE_CHAT("delete:chat"),
    BLOCK_USER("block:user"),
    READ_USER("read:user"),
    WRITE_USER("write:user");


    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission () {
        return permission;
    }
}
