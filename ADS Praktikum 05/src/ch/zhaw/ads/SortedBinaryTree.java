package ch.zhaw.ads;

public class SortedBinaryTree<T extends Comparable<T>> implements Tree<T> {
    protected TreeNode<T> root;

    private TreeNode<T> insertAt(TreeNode<T> node, T x) {
        if (node == null) {
            return new TreeNode<>(x);
        } else {
            if (x.compareTo(node.getValue()) <= 0) {
                node.left = insertAt(node.left, x);
            } else {
                node.right = insertAt(node.right, x);
            }
            return node;
        }
    }

    public void add(T x) {
        root = insertAt(root, x);
    }

    /**
     * Find node to replace
     */
    private TreeNode<T> findRepAt(TreeNode<T> node, TreeNode<T> rep) {
        if (node.right != null) {
            node.right = findRepAt(node.right, rep);
        } else {
            rep.values = node.values;
            node = node.left;
        }
        return node;
    }

    /**
     * Remove node
     */
    private TreeNode<T> removeAt(TreeNode<T> node, T x, TreeNode<T> removed) {
        if (node == null) {
            return null;
        } else {
            if (x.compareTo(node.getValue()) == 0) {
                // found
                removed.values = node.values;
                if (node.left == null) {
                    node = node.right;
                } else if (node.right == null) {
                    node = node.left;
                } else {
                    node.left = findRepAt(node.left, node);
                }
            } else if (x.compareTo(node.getValue()) < 0) {
                // search left
                node.left = removeAt(node.left, x, removed);
            } else {
                // search right
                node.right = removeAt(node.right, x, removed);
            }
            return node;
        }
    }

    public T remove(T x) {
        TreeNode<T> removed = new TreeNode<>(null);
        root = removeAt(root, x, removed);
        return removed.getValue();
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Traversal<T> traversal() {
        return new TreeTraversal<>(root);
    }

    protected int calcHeight(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = calcHeight(node.left);
        int rightHeight = calcHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int height() {
        return calcHeight(root);
    }

    protected int calcSize(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }

        int leftSize = calcSize(node.left);
        int rightSize = calcSize(node.right);

        return leftSize + rightSize + 1;
    }

    public int size() {
        return calcSize(root);
    }

    public boolean balanced() {
        return false;
    }

    /**
     * Only for testing and debugging: show the structure of the tree
     */
    public String printTree() {
        StringBuilder out = new StringBuilder();
        if (root.right != null) {
            printTree(root.right, out, true, "");
        }
        out.append(root.values).append("\n");
        if (root.left != null) {
            printTree(root.left, out, false, "");
        }
        return out.toString();
    }

    private void printTree(TreeNode<T> node, StringBuilder out, boolean isRight, String indent) {
        if (node.right != null) {
            printTree(node.right, out, true,
                    indent + (isRight ? "        " : " |      "));
        }
        out.append(indent);
        if (isRight) {
            out.append(" /");
        } else {
            out.append(" \\");
        }
        out.append("----- ");
        out.append(node.values).append("\n");
        if (node.left != null) {
            printTree(node.left, out, false,
                    indent + (isRight ? " |      " : "        "));
        }
    }
}