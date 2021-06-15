package com.lts.model.wrapper;

import java.util.List;

public class NavigationTreeNode {

    private String text;
    private String url;
    private String icon;
    private List<NavigationTreeNode> nodes;

    public NavigationTreeNode(String text, String icon, String url) {
        this.text = text;
        this.icon = icon;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }

    public List<NavigationTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<NavigationTreeNode> nodes) {
        this.nodes = nodes;
    }
}
