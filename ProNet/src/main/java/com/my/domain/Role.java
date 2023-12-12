package com.my.domain;

public enum Role {
    ADMINISTRATOR("admin"),
    SUBSCRIBER("subscriber");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public int getId() {
        return ordinal();
    }

    public String getName() {
        return name;
    }
}
