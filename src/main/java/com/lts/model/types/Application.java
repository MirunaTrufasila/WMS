package com.lts.model.types;

public enum Application {

    NONE(0, ""),
    LTS(1, "lts");

    private final int id;
    private final String contextPath;

    Application(int id, String contextPath) {
        this.id = id;
        this.contextPath = contextPath;
    }

    public int getId() {
        return id;
    }

    public String getContextPath() {
        return contextPath;
    }
}
