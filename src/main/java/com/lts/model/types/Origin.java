package com.lts.model.types;

public enum Origin {

    NONE(0),
    LTS(1);

    private int id;

    Origin(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
