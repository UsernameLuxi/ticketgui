package com.example.ticketgui.BE;

public enum UserRole {
    ADMIN(1),
    EVENT_KOORDINATOR(2);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public static int getUserID(UserRole role) {
        return role.getId();
    }
}
