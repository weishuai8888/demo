package com.weishuai.algorithm.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * 定义树结构
 * @author ws001
 */
@Getter
@Setter
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

}
