package com.lts.util;

import com.lts.model.entities.Privilege;
import com.lts.model.entities.PrivilegeNode;
import com.lts.model.entities.PrivilegeNodeState;
import com.lts.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserPrivilegesUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserPrivilegesUtil.class);

    private UserPrivilegesUtil() {
        // prevents instantiation
    }

    public static Collection<PrivilegeNode> getPrivilegeTree(List<Privilege> allPrivileges,
                                                             Map<Long, Boolean> privilegesMap,
                                                             MessageService messageService) {
        Map<Long, PrivilegeNode> nodeMap = new TreeMap<>();

        // first pass - we form the nodes and put them into a map, for quick lookup
        // here we can also add additional description like id, for debugging
        for (Privilege privilege : allPrivileges) {
            PrivilegeNode privilegeNode;
            if (privilege.getIdParinte() == 0) { //modul
                privilegeNode = new PrivilegeNode(
                        privilege.getId(),
                        privilege.getIdParinte(),
                        privilege.getDescription(),
                        new PrivilegeNodeState(privilegesMap.get(privilege.getId()) != null),
                        "green");
            } else {
                privilegeNode = new PrivilegeNode(
                        privilege.getId(),
                        privilege.getIdParinte(),
                        privilege.getDescription(),
                        new PrivilegeNodeState(privilegesMap.get(privilege.getId()) != null));
            }
            nodeMap.put(privilegeNode.getId(), privilegeNode);
        }

        // second pass - we compose the children array to each node
        for (PrivilegeNode node : nodeMap.values()) {
            while (node.getParentId() > 0) {
                PrivilegeNode parent = nodeMap.get(node.getParentId());
                if (parent == null) {
                    logger.error(messageService.getMessage("errBadParent", node.getId(), node.getParentId()));
                    break;
                }
                if (parent.getNodes() == null) {
                    parent.setNodes(new ArrayList<>());
                }
                if (!parent.getNodes().contains(node)) {
                    parent.getNodes().add(node);
                }
                node = parent;
            }
        }

        // finally, we return only the first level nodes (which contains all the other nodes as child/grandchild, etc)
        List<PrivilegeNode> result = new ArrayList<>();
        for (PrivilegeNode node : nodeMap.values()) {
            if (node.getParentId() == 0) {
                result.add(node);
            }
        }

        return result;
    }
}
