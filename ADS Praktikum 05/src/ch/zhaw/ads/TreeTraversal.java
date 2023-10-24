package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.Queue;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {

    private final TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    @Override
    public void preorder(Visitor<T> visitor) {
        preorder(root, visitor);
    }

    private void preorder(TreeNode<T> node, Visitor<T> visitor) {
        if (node != null) {
            visitor.visit(node.getValue());
            preorder(node.left, visitor);
            preorder(node.right, visitor);
        }
    }

    @Override
    public void inorder(Visitor<T> visitor) {
        inorder(root, visitor);
    }

    private void inorder(TreeNode<T> node, Visitor<T> visitor) {
        if (node != null) {
            inorder(node.left, visitor);
            visitor.visit(node.getValue());
            inorder(node.right, visitor);
        }
    }

    @Override
    public void postorder(Visitor<T> visitor) {
        postorder(root, visitor);
    }

    private void postorder(TreeNode<T> node, Visitor<T> visitor) {
        if (node != null) {
            postorder(node.left, visitor);
            postorder(node.right, visitor);
            visitor.visit(node.getValue());
        }
    }

    @Override
    public void levelorder(Visitor<T> visitor) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.poll();
            visitor.visit(node.getValue());

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    @Override
    public void interval(T min, T max, Visitor<T> visitor) {
        interval(root, min, max, visitor);
    }

    private void interval(TreeNode<T> node, T min, T max, Visitor<T> visitor) {
        if (node == null) {
            return;
        }

        if (node.getValue().compareTo(min) >= 0) {
            interval(node.left, min, max, visitor);
        }

        if (node.getValue().compareTo(min) >= 0 && node.getValue().compareTo(max) <= 0) {
            visitor.visit(node.getValue());
        }

        if (node.getValue().compareTo(max) <= 0) {
            interval(node.right, min, max, visitor);
        }
    }
}