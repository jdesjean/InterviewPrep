package org.ip.leetcode;

import java.util.HashSet;
import java.util.Set;

import org.ip.tree.Node;

// Leetcode 968
public class MinCameraCovered {
	int ans;
    Set<Node> covered;
    public int minCameraCover(Node root) {
        ans = 0;
        covered = new HashSet();
        covered.add(null);

        dfs(root, null);
        return ans;
    }

    public void dfs(Node node, Node par) {
        if (node == null) return;
        dfs(node.getLeft(), node);
        dfs(node.getRight(), node);

        if (par == null && !covered.contains(node) ||
                !covered.contains(node.getLeft()) ||
                !covered.contains(node.getRight())) {
            ans++;
            covered.add(node);
            covered.add(par);
            covered.add(node.getLeft());
            covered.add(node.getRight());
        }
    }
}
