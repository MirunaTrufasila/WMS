package com.lts.model.wrapper;

public class UserDto {

    private long id;
    private String name;
    private String groupName;

    public UserDto() {
        // necessary for deserialization
    }

    public UserDto(long id, String name, String groupName) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }
}
