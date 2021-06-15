package com.lts.model.wrapper;

import java.util.List;

public class TreeNode {

    private long id;
    private long parentId;
    private String text;
    private String icon; // ex: icon: "glyphicon glyphicon-stop"
    private String selectedIcon; // ex: selectedIcon: "glyphicon glyphicon-stop"
    private String color; // ex: color: "#000000"
    private String backColor; // ex: backColor: "#FFFFFF"
    private String href; // ex: href: "#node-1",
    private boolean selectable;
    private TreeNodeState state;
    private List<TreeNode> nodes;
    private String additionalData;
    private long platformParentId;

    public TreeNode(long id, long parentId, String text, TreeNodeState state) {
        this(id, parentId, text, state, null);
    }

    public TreeNode(long id, long parentId, String text, TreeNodeState state, String color) {
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

    public TreeNodeState getState() {
        return state;
    }

    public void setState(TreeNodeState state) {
        this.state = state;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public long getPlatformParentId() {
        return platformParentId;
    }

    public void setPlatformParentId(long platformParentId) {
        this.platformParentId = platformParentId;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                ", selectedIcon='" + selectedIcon + '\'' +
                ", color='" + color + '\'' +
                ", backColor='" + backColor + '\'' +
                ", href='" + href + '\'' +
                ", selectable=" + selectable +
                ", state=" + state +
                ", nodes=" + nodes +
                '}';
    }
}