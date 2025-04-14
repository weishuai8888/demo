package com.weishuai.algorithm.tree;

/**
 * 求二叉树最长路径（非根到叶子）
 * @author ws001
 */
public class BinaryTreeMaxDiameter {

    private static int maxDiameter;

    public static int getMaxDiameter(TreeNode root){
        dfs(root);
        return maxDiameter;
    }

    private static int dfs(TreeNode node) {
        // node为null，直接返回0
        if (node == null) {
            return 0;
        }
        // 深度遍历左子树
        int left = dfs(node.left);
        // 深度遍历右子树
        int right = dfs(node.right);
        // 更新最长路径
        maxDiameter = Math.max(maxDiameter, left + right);
        // 返回当前节点的最大深度
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        TreeNode one = new TreeNode(1);
        one.left = new TreeNode(2);
        one.left.left = new TreeNode(4);
//        one.left.left.left = new TreeNode(6);
        one.left.right = new TreeNode(5);
        one.right = new TreeNode(3);
        int maxDiameter = getMaxDiameter(one);
        System.out.println(maxDiameter);
    }
}
