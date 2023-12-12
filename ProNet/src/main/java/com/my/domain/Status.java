package com.my.domain;

public enum Status {
    BLOCKED("Blocked"),
    UNBLOCKED("Unblocked");

    private String name;

    private Status(String name) {
        this.name = name;
    }

    public int getId() {
        return ordinal();
    }

    public String getName() {
        return name;
    }
}
