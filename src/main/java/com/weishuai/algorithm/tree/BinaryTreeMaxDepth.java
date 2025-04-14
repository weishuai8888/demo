package com.weishuai.algorithm.tree;

/**
 * 求二叉树的最大深度
 * @author ws001
 */
public class BinaryTreeMaxDepth {

    public static int maxDepth(TreeNode root){
        return dfs(root);
    }

    private static int dfs(TreeNode node) {
        // 当前树节点为null, 直接返回0
        if (node == null) {
            return 0;
        }
        // 深度遍历左节点
        int left = dfs(node.left);
        // 深度遍历右节点
        int right = dfs(node.right);
        // 更新最大深度并返回
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        TreeNode one = new TreeNode(1);
        one.left = new TreeNode(2);
        one.left.left = new TreeNode(4);
        one.left.right = new TreeNode(5);
        one.left.right.right = new TreeNode(6);
        one.right = new TreeNode(3);
        int maxDepth = BinaryTreeMaxDepth.maxDepth(one);
        System.out.println(maxDepth);
    }
}
