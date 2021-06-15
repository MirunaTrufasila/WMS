package com.lts.model.entities;

import java.util.List;

public class PrivilegeNode {

    private long id;
    private long parentId;
    private String text;
    private PrivilegeNodeState state;
    private List<PrivilegeNode> nodes;
    private String color;

    public PrivilegeNode(long id, long parentId, String text, PrivilegeNodeState state) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.state = state;
    }

    public PrivilegeNode(long id, long parentId, String text, PrivilegeNodeState state, String color) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.state = state;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PrivilegeNodeState getState() {
        return state;
    }

    public void setState(PrivilegeNodeState state) {
        this.state = state;
    }

    public List<PrivilegeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<PrivilegeNode> nodes) {
        this.nodes = nodes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
