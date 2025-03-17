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

    // ? - måske fjern
    public static int getUserID(UserRole role) {
        return role.getId();
    }

    public static UserRole getUserRole(int id) {
        for (UserRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return EVENT_KOORDINATOR;
    }

    public static UserRole getRoleByString(String role){
        role = role.toUpperCase();
        for (UserRole userRole : values()) {
            if(userRole.toString().equals(role)){
                return userRole;
            }
        }
        return EVENT_KOORDINATOR;
    }
}
