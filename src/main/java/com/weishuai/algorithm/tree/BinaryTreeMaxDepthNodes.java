package com.weishuai.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 求二叉树最大深度路径上的节点列表
 * @author ws001
 */
public class BinaryTreeMaxDepthNodes {

    private static List<TreeNode> nodeList;

    public static List<TreeNode> getMaxDepthNodes(TreeNode root){
        int maxDepth = dfs(root);
        maxDepthNodes(root, 1, maxDepth, new ArrayList<TreeNode>());
        return nodeList;
    }

    private static void maxDepthNodes(TreeNode node, int curDepth, int maxDepth, ArrayList<TreeNode> tmpNodes) {
        // 如果node位null，直接返回空List
        if (node == null) {
            return;
        }
        // 将node添加进临时节点列表
        tmpNodes.add(node);
        // 如果当前节点左右子树都为null，判断是否到最大深度
        if (node.left == null && node.right == null) {
            //当当前节点是叶子结点且到最大深度则将临时节点列表赋值给nodeList,并return
            if (curDepth == maxDepth) {
                nodeList = tmpNodes;
                return;
            }
        }
        // 左子结点深度调用当前方法, curDepth + 1, new ArrayList(tmpNodes)
        maxDepthNodes(node.left, curDepth + 1, maxDepth, new ArrayList<>(tmpNodes));
        // 右子节点深度调用当前方法
        maxDepthNodes(node.right, curDepth + 1, maxDepth, new ArrayList<>(tmpNodes));
    }

    private static int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = dfs(node.left);
        int right = dfs(node.right) ;
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        TreeNode one = new TreeNode(1);
        one.left = new TreeNode(2);
        one.left.left = new TreeNode(4);
        one.left.left.left = new TreeNode(6);
        one.left.right = new TreeNode(5);
        one.right = new TreeNode(3);
        for (TreeNode node : getMaxDepthNodes(one)) {
            System.out.print(node.val + " ");
        }

    }
}
